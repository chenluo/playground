package com.chenluo.pattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Singleton {
    private static Singleton instance = null;
    private final int[] heap = new int[1_000_000_000];

    private Singleton() {

    }

    public static Singleton getInstance() {
        Thread.yield();
        if (instance == null) {
            synchronized (Singleton.class) {
                Thread.yield();
                if (instance == null) {
                    System.out.println("start init");
                    instance = new Singleton();
                    System.out.println("end init");
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 200; i++) {
            Future<?> submit = executorService.submit(() -> {
                System.out.println(Singleton.getInstance().init());
            });
        }
        executorService.shutdown();
        while (!executorService.awaitTermination(100, TimeUnit.SECONDS)) {

        }
        System.out.println("exit");
    }

    public boolean init() {
        heap[100] = 1;
        return true;
    }

}
