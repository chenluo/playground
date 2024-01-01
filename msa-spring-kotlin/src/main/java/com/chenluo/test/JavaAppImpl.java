package com.chenluo.test;

public class JavaAppImpl implements JavaApp {
    public static void main(String[] args) {
        new JavaAppImpl().run();
        JavaApp javaApp = new JavaAppImpl();
        javaApp.run();
    }

    @Override
    public void run() {
        this.util();
    }

    private void util() {}
}
