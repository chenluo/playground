package com.chenluo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulateConcurrentCollision {
    public static void main(String[] args) throws InterruptedException {
        int totalIter= 10;
        int curIter = 0;
        int userCount = 100;
        int scale = 1000; // scale full day down
        int secPerIter= 3600*8;
        int totalActionCount = 1000;
        int actionDuration = secPerIter*1000/totalActionCount/userCount; // ms
        AtomicInteger colusion = new AtomicInteger();
        while (curIter < totalIter) {
            curIter++;
            ThreadPoolExecutor users = new ThreadPoolExecutor(userCount, userCount, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
            Map<Long, List<Integer>> store = new ConcurrentHashMap<>();
            for (int i = 0; i < userCount; i++) {
                int finalI = i;
                users.submit(() -> {
                    Instant start = Instant.now();
                    while (Instant.now().isBefore(start.plusSeconds(secPerIter/scale))) {
                        try {
                            Thread.sleep(actionDuration/scale +Math.round(Math.random()*actionDuration/scale));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        long key = Instant.now().toEpochMilli()/1000; // key in second
                        store.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>());
                        store.get(key).add(finalI);
                    }
                });
            }
            users.shutdown();
            users.awaitTermination(120, TimeUnit.SECONDS);
            System.out.println(store.values().stream().map(it -> it.size()).reduce(0, (old, l) -> old + l));
            if (store.entrySet().stream().anyMatch(it -> !it.getValue().isEmpty())) {
                System.out.println("has collusion");
                colusion.incrementAndGet();
            }
        }
        System.out.printf("rate: %s/%s%n", colusion, totalIter);
    }
}
