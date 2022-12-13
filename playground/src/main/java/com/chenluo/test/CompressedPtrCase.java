package com.chenluo.test;

public class CompressedPtrCase {
    // -Xmx4G -Xms4G -verbose:gc -Xloggc:compressedPtr.gc.log
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        for (int i = 0; i < 1_000_000; i++) {
            Object[] ints = new Object[1_000_000];
            ints[0] = 1;
            System.out.println(ints[0]);
        }
    }
}

