package com.chenluo.jvm.learn.gc;

// javap -v TestGcLog.class
//-Xmx50M -Xms50M -XX:+PrintGCDetails
public class TestGcLog {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            byte[] arr = new byte[1024*1024];
            Thread.sleep(100);
        }
    }
}
