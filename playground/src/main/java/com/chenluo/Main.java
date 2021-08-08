package com.chenluo;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.*;

public class Main {

    private final String lock = "Thisisalock";

    private volatile int volatileInt = 0;

    private int nonVolatileInt = 0;

    public static void main(String[] args) {
        new Main().testMarkWord();
    }

    private void testMarkWord() {
        volatileInt = 1;
        nonVolatileInt = 2;
        System.out.println("init");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        System.out.println("single thread enter the critical area");
        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }

        ExecutorService executor = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.AbortPolicy());
        Runnable t1 = () -> {
            int count = 1;
            while (count-- != 0) {
                synchronized (lock) {
                    System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
            }
        };

        Callable<Boolean> callable = () -> {
            try {
                t1.run();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        };

        Runnable t2 = () -> {
            int count = 1;
            while (count-- != 0) {
                synchronized (lock) {
                    System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
            }

        };

        executor.execute(t1);
        executor.execute(t2);

        executor.shutdown();

    }

}
