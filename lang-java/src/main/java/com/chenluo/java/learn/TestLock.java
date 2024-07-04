/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.java.learn;

import java.util.concurrent.locks.ReentrantLock;

/**
 * run with VM Option:
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:-Inline
 */
public class TestLock {
    private static Object lock = new Object();
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static int a = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            lockBySync();
            lockByLock();
            reentrantLock.lock();
            reentrantLock.lockInterruptibly();
        }
    }

    private static void lockBySync() {
        synchronized (lock) {
            a++;
        }
    }

    private static void lockByLock() {
        if (reentrantLock.tryLock()) {
            a++;
        }
    }
}
