package com.chenluo.service;

import org.apache.zookeeper.KeeperException;

public interface ZKManager {
    void closeConnection();

    public void create(String path, byte[] data) throws KeeperException, InterruptedException;

    boolean isExist(String path) throws KeeperException, InterruptedException;

    public Object getZNodeData(String path, boolean watchFlag)
            throws KeeperException, InterruptedException;

    public void update(String path, byte[] data) throws KeeperException, InterruptedException;

    String lock(String path);

    boolean unlock(String lock);

    void updateCAS(String path, byte[] data) throws KeeperException, InterruptedException;
}
