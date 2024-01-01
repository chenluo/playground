package com.chenluo.java.learn.virtualthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VirtualThreadTry {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1; i++) {
                int finalI = i;
                executorService.execute(() -> {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread());
                        Thread.dumpStack();
                        System.out.println("print in vt" + finalI);
                    }
                });
            }
            executorService.shutdown();
            boolean b = executorService.awaitTermination(16, TimeUnit.SECONDS);
            if (!b) {
                System.out.println("failed to terminate");
                System.out.println("Forced to shutdown");
                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
