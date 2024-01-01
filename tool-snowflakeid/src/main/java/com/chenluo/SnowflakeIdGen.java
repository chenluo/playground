package com.chenluo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class SnowflakeIdGen {
    private final AtomicInteger seq = new AtomicInteger();
    private Long machineId;
    private long timestamp = 0L;
    private int maxSeq = 0;
    private int seqBits = 14;
    private int machineIdBits = 8;
    private int timestampBits = 64 - machineIdBits - seqBits;


    public SnowflakeIdGen() {
        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;
        RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
        CuratorFramework curator = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        curator.start();
        try {
            String prefix = "/snowflake/machineId-";
            String machineId = curator.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(prefix, new byte[0]);
            String no = machineId.substring(prefix.length());
            setMachineId(Long.parseLong(no));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SnowflakeIdGen(Long machineId) {
        assertMachineId(machineId);
        this.machineId = machineId;
    }

    private void assertMachineId(Long machineId) {
        if (machineId >= 1L << machineIdBits) {
            throw new RuntimeException("too many machines");
        }
    }

    public synchronized void setMachineId(long machineId) {
        assertMachineId(machineId);
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long cur = Instant.now().toEpochMilli();
        if (timestamp < cur) {
            timestamp = cur;
            if (seq.get() > maxSeq) {
                System.out.println(seq.get());
                maxSeq = seq.get();
            }
            seq.set(0);
        }
        int nextSeq = seq.incrementAndGet();
        if (nextSeq >= (1 << seqBits)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return nextId();
        }
        long l = (timestamp << seqBits + machineIdBits) | machineId << seqBits | nextSeq;
        return l;
    }
}
