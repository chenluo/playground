package com.chenluo;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class SnowflakeIdGen {
    private final String machineId;
    private final AtomicInteger seq = new AtomicInteger();
    private long timestamp = 0L;
    private int maxSeq = 0;

    public SnowflakeIdGen(String machineId) {
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

        long l = (timestamp << 20) | (Integer.parseInt(machineId) << 16) | (seq.incrementAndGet());
        return l;
    }
}
