package com.chenluo.zk.connection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZKConnection {
    private ZooKeeper zoo;
    CountDownLatch connectionLatch = new CountDownLatch(1);
    private final Logger logger = LoggerFactory.getLogger(ZKConnection.class);
    

    public ZooKeeper connect(String host)
            throws IOException,
            InterruptedException {
        zoo = new ZooKeeper(host+":2181", 20000, new Watcher() {
            public void process(WatchedEvent we) {
                if (we.getState().equals(Event.KeeperState.SyncConnected)) {
                    logger.info("connected.");
                    connectionLatch.countDown();
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
