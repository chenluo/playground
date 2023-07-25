package com.chenluo;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RedissonLockTester {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final RedissonClient client = RedissonFactory.get();
        String lockName = "lock";
        RLock lock = client.getLock(lockName);
        lock.lock();
        System.out.println("[main] get lock");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            RLock lock1 = client.getLock(lockName);
            while (lock1.isLocked()) {
                System.out.println("[sub1] still locked");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("[sub1] exit");
        });
        CompletableFuture.runAsync(() -> {
            System.out.println("[sub2] sleep");
            try {
                Thread.sleep(40_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[sub2] awake");

        }).get();
        lock.unlock();
        System.out.println("[main] unlock");
        Void unused = future.get();
        System.out.println("[main] exit");
        client.shutdown();
    }
}
