package com.chenluo.service;

import com.chenluo.zk.connection.ZKConnection;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.LockSupport;

public class ZKManagerImpl implements ZKManager {
    private ZooKeeper zkeeper;
    private ZKConnection zkConnection;
    private final ZKConfiguration zkConfiguration;
    private final Logger logger = LoggerFactory.getLogger(ZKManagerImpl.class);

    public ZKManagerImpl(ZKConfiguration zkConfiguration) throws IOException, InterruptedException, KeeperException {
        this.zkConfiguration = zkConfiguration;
        initialize();
    }

    private void initialize() throws IOException, InterruptedException, KeeperException {
        this.zkConnection = new ZKConnection();
        this.zkeeper = zkConnection.connect(zkConfiguration.getHost());
    }

    @Override
    public void closeConnection() {
        try {
            zkConnection.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String path, byte[] data)
            throws KeeperException,
            InterruptedException {
        zkeeper.create(
                path,
                data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    @Override
    public boolean isExist(String path)
            throws KeeperException,
            InterruptedException {
        return Objects.nonNull(zkeeper.exists(path, false));
    }

    @Override
    public Object getZNodeData(String path, boolean watchFlag) throws KeeperException, InterruptedException {
        byte[] b = null;
        b = zkeeper.getData(path, null, null);

        return new String(b, StandardCharsets.UTF_8);
    }

    @Override
    public void update(String path, byte[] data) throws KeeperException,
            InterruptedException {
        int version = zkeeper.exists(path, true).getVersion();
        zkeeper.setData(path, data, version);
    }

    @Override
    public String lock(String path) {
        if (!this.zkeeper.getState().equals(ZooKeeper.States.CONNECTED)) {
            try {
                this.initialize();
            } catch (IOException | InterruptedException | KeeperException e) {
                e.printStackTrace();
                return null;
            }
        }
        String lockPath = null;
        try {
            lockPath = zkeeper.create(path + "/lock_", "".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException e) {
            logger.error("", e);
            return null;
        } catch (InterruptedException e) {
            logger.error("", e);
            Thread.interrupted();
            return null;
        }
        try {
            while (true) {
                Thread currentThread = Thread.currentThread();
                List<String> children = zkeeper.getChildren(path, false);
                Collections.sort(children);
                if (lockPath.endsWith(children.get(0))) {
                    logger.info("get lock directly: {}", lockPath);
                    return lockPath;
                }
                String last = null;
                for (String child : children) {
                    if (lockPath.endsWith(child)) {
                        break;
                    }
                    last = child;
                }
                logger.warn("get lock: {} but last lock is: {}", lockPath, last);
//                Stat stat = zkeeper.exists(path + "/" + last, event -> {
//                    if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
//                        logger.info("wake up: {}", currentThread);
//                        LockSupport.unpark(currentThread);
//                    }
//                });

                LockSupport.park();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return lockPath;
    }

    @Override
    public boolean unlock(String lock) {
        boolean success = false;
        int retry = 1;
        while (retry > 0) {
            try {
                retry--;
                zkeeper.delete(lock, -1);
                success = true;
            } catch (KeeperException e) {
                logger.error("[{}] failed to unlock:{}.", retry, lock, e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    @Override
    public void updateCAS(String path, byte[] data) throws InterruptedException {
        int retry = 100;
        while (retry > 0) {
            try {
                int version = zkeeper.exists(path, true).getVersion();
                zkeeper.setData(path, data, version);
            } catch (KeeperException e) {
                logger.error("retrying: {}", retry);
            }
            retry--;
        }
    }
}
