package com.chenluo.java.learn;

public class TestMethodCallAsMemoryBarrier {

    public static void main(String[] args) {
        new TestMethodCallAsMemoryBarrier().run();
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            doRun();
        }
    }

    private void doRun() {
        int longVariableName = 0x1111;
        longVariableName = 0x1112;
        anyMethod(longVariableName);
        longVariableName = longVariableName + 1;
    }

    private void anyMethod(int num) {
    }
}
