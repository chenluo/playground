package com.chenluo.zk.connection;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public class ZKConnection {
    private final Logger logger = LoggerFactory.getLogger(ZKConnection.class);
    CountDownLatch connectionLatch = new CountDownLatch(1);
    private ZooKeeper zoo;

    public static void main(String[] args)
            throws IOException, InterruptedException, KeeperException {
        ZKConnection zkConnection = new ZKConnection();
        zkConnection.connect("localhost");
        String s1 = zkConnection.zoo.create("/test/test-", "seq".getBytes(), OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(s1);
        String s2 = zkConnection.zoo.create("/test/test-", "seq".getBytes(), OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(s2);
        String s3 = zkConnection.zoo.create("/test/test-", "seq".getBytes(), OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(s3);
        String s4 = zkConnection.zoo.create("/test/test-", "seq".getBytes(), OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(s4);

        Stat stat = new Stat();
        String s5 = zkConnection.zoo.create("/test/test-", "seq".getBytes(), OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL, stat);
        System.out.println(s5);
        zkConnection.logger.info("{}", stat);
    }

    public ZooKeeper connect(String host) throws IOException, InterruptedException {
        zoo = new ZooKeeper(host + ":2181", 20000, new Watcher() {
            public void process(WatchedEvent we) {
                if (we.getState().equals(Event.KeeperState.SyncConnected)) {
                    logger.info("connected.");
                    connectionLatch.countDown();
                } else {
                    logger.info("{}", we);
                }
            }
        });

        connectionLatch.await();
        return zoo;
    }

    public void close() throws InterruptedException {
        zoo.close();
    }
}
