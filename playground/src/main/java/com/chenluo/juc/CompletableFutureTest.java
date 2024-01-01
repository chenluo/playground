package com.chenluo.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletableFutureTest {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.runAsync(
                () -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.exit(0);
                });
        while (true) {
            Thread.sleep(1);
            CompletableFuture<Void> voidCompletableFuture =
                    new CompletableFutureTest().tryCompletableFuture();
            if (voidCompletableFuture.isCompletedExceptionally()) {
                System.out.println("bingo");
                break;
            }
        }
    }

    public CompletableFuture<Void> tryCompletableFuture() {
        AtomicInteger integer = new AtomicInteger(0);
        CompletableFuture<Void> voidCompletableFuture =
                CompletableFuture.runAsync(
                                () -> {
                                    if (integer.incrementAndGet() != 1) {
                                        throw new RuntimeException("failed at step 0");
                                    }
                                })
                        .thenAcceptAsync(
                                (s) -> {
                                    if (integer.incrementAndGet() != 2) {
                                        throw new RuntimeException("failed at step 1");
                                    }
                                })
                        .thenAcceptAsync(
                                (s) -> {
                                    if (integer.incrementAndGet() != 3) {
                                        throw new RuntimeException("failed at step 1");
                                    }
                                })
                        .thenAcceptAsync(
                                (s) -> {
                                    if (integer.incrementAndGet() != 4) {
                                        throw new RuntimeException("failed at step 1");
                                    }
                                });
        return voidCompletableFuture;
    }
}
