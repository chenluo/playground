package com.chenluo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SnowflakeIdGenTest {
    static GenericContainer<?> zookeeper =
            new GenericContainer<>("zookeeper:3.8.0").withExposedPorts(2181);
    Map<Long, Integer> generated = new ConcurrentHashMap<>();

    @BeforeAll
    public static void setup() {
        zookeeper.start();
    }

    @AfterAll
    public static void tearDown() {
        zookeeper.close();
    }

    @Test
    public void testNextId() throws InterruptedException {
        Long start = Instant.now().toEpochMilli();
        int instance = 1;
        int thread = 10;
        int cnt = 1000_000;
        ExecutorService executor = Executors.newFixedThreadPool(16);
        String connectionString = zookeeper.getHost() + ":" + zookeeper.getMappedPort(2181);

        for (int i = 0; i < instance; i++) {
            SnowflakeIdGen gen = new SnowflakeIdGen(connectionString);
            for (int j = 0; j < thread; j++) {
                executor.submit(() -> {
                    for (int k = 0; k < cnt; k++) {
                        generated.put(gen.nextId(), 1);
                    }
                });
            }
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        Long end = Instant.now().toEpochMilli();
        int total = instance * thread * cnt;
        System.out.println("%d ids generated in %d ms".formatted(total, end - start));
        System.out.println("%d per ms.".formatted(total / (end - start)));
        System.out.println("actual %d ids generated".formatted(generated.size()));
        assert total == generated.size();
    }
}
