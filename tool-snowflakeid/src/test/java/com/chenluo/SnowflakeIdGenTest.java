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
        int instance = 1;
        int thread = 10;
        int cnt = 1000_000;
        ExecutorService executor = Executors.newFixedThreadPool(16);

        for (int i = 0; i < instance; i++) {
            SnowflakeIdGen gen = new SnowflakeIdGen();
            for (int j = 0; j < thread; j++) {
                executor.submit(() -> {
                    for (int k = 0; k < cnt; k++) {
                        generated.put(gen.nextId(), 1);
                    }
                });
            }
        }
        executor.shutdown();
        while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {

        }
        Long end = Instant.now().toEpochMilli();
        int total = instance * thread * cnt;
        System.out.println("%d ids generated in %d ms".formatted(total, end - start));
        System.out.println("%d per ms.".formatted(total / (end - start)));
        System.out.println("actual %d ids generated".formatted(generated.size()));
        assert total == generated.size();
    }

}