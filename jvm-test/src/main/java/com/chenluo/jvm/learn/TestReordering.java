package com.chenluo.jvm.learn;

import java.util.concurrent.Semaphore;

public class TestReordering {
    public static void main(String[] args) throws InterruptedException {
        new TestReordering().test();
    }

    private void run() throws InterruptedException {
        final int[] ordinaryInt = {0};
        Thread readT = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                int prev = ordinaryInt[0];
                int next = ordinaryInt[0];
                if (prev > next) {
                    System.out.println("reordering occurred.");
                }
            }

        });

        Thread writeT = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    ordinaryInt[0]++;
                }
            }
        });
        readT.start();
        writeT.start();


        Thread.sleep(100000);
        readT.interrupt();
        writeT.interrupt();
        System.out.println("finished.");
    }

    private void test() {
        new TwoFieldClass().test();
    }

    class TwoFieldClass {
        volatile int a = 0;
        int b = 0;
        int r1 = 0;
        int r2 = 0;

        Semaphore semaphore1 = new Semaphore(1);

        Semaphore semaphore2 = new Semaphore(1);

        Semaphore semaphore3 = new Semaphore(1);

        Semaphore semaphore4 = new Semaphore(1);

        public void test() {
            Thread readT = new Thread(this::change1);
            Thread writeT = new Thread(this::change2);
            semaphore1.acquireUninterruptibly();
            semaphore3.acquireUninterruptibly();
            semaphore2.acquireUninterruptibly();
            semaphore4.acquireUninterruptibly();
            readT.start();
            writeT.start();

            int detected = 0;

            for (int i = 0; ; i++) {
                a = 0;
                b = 0;
                semaphore1.release();
                semaphore3.release();

                semaphore2.acquireUninterruptibly();
                semaphore4.acquireUninterruptibly();
                if (r1 == 0 && r2 == 0) {
                    // TODO: visiblity issue or just reorder?
                    System.out.println(detected++ + " reorder occur at iter " + i);
                }
            }

        }

        public void change1() {
            while (true) {
                semaphore1.acquireUninterruptibly();
                a = 1;
                r1 = b;
                semaphore2.release();
            }
        }

        public void change2() {
            while (true) {
                semaphore3.acquireUninterruptibly();
                b = 1;
                r2 = a;
                semaphore4.release();
            }

        }

        private void anyMethodAsBarrier() {
            a+=0;
            b+=0;
            r1+=0;
            r2+=0;
        }
    }
}
