package com.chenluo.jvm.learn;

import java.util.concurrent.Semaphore;

// https://stackoverflow.com/questions/23603304/java-8-unsafe-xxxfence-instructions
public class TestReordering {
    public static void main(String[] args) throws InterruptedException {
        new TestReordering().test();
    }


    // invalid test.
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
        int a = 0;
        int b = 0;
        volatile int r1 = 0;
        volatile int r2 = 0;

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
                    // Update: no visibility issue here because semaphore has similar semantics of synchronized.
                    System.out.println(detected++ + " reorder occur at iter " + i);
                }
            }

        }

        public void change1() {
            while (true) {
                semaphore1.acquireUninterruptibly();
                a = 1; // store a
//                anyMethodAsBarrier();
                r1 = b; // load b, store r1
                // load b -> store a -> store r1
                semaphore2.release();
            }
        }

        public void change2() {
            while (true) {
                semaphore3.acquireUninterruptibly();
                b = 1; // store b
//                anyMethodAsBarrier();
                r2 = a;// load a, store r2
                semaphore4.release();
            }

        }
        // change 1 & 2:
        // t1           t2
        // load b       store b
        // store a      load a
        // store r1     store r2

        private synchronized void anyMethodAsBarrier() {
            a+=0;
            b+=0;
            r1+=0;
            r2+=0;
        }
    }
}
