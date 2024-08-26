package com.chenluo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class HashMapMultiThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Map<Integer, Integer> map = new HashMap<>();

//        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        int count = 100;
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            executorService.submit(() -> {
                for (int j = 0; j < count; j++) {
                    map.put(j, j);
                }
                System.out.println("%s done".formatted(finalI));
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.SECONDS);
        if (map.size() != count) {
            System.out.println("data loss");
            throw new RuntimeException("data loss");
        }
    }
}
