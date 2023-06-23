package com.chenluo;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class SnowflakeIdGenTest {
    Map<Long, Integer> generated = new ConcurrentHashMap<>();

    @Test
    public void testNextId() throws InterruptedException {
        Long start = Instant.now().toEpochMilli();
        int parallel = 1;
        int cnt = 100_000_000;
        ExecutorService executor = Executors.newFixedThreadPool(16);
        SnowflakeIdGen gen = new SnowflakeIdGen(String.valueOf(1));

        for (int i = 0; i < parallel; i++) {
            int finalI = i;
            executor.submit(() -> {
                for (int j = 0; j < cnt; j++) {
                    generated.put(gen.nextId(), 1);
                }
            });
        }
        executor.shutdown();
        while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {

        }
        Long end = Instant.now().toEpochMilli();
        System.out.println("%d ids generated in %d ms".formatted(parallel * cnt, end - start));
        System.out.println("%d per ms.".formatted(parallel * cnt / (end - start)));
        System.out.println("actual %d ids generated".formatted(generated.size()));
        assert parallel * cnt == generated.size();
    }

}