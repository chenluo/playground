package com.chenluo.java.learn;

public class TestStaticVariable {
    static int a = 0;
    static int b = 0;

    static {
        a = 1;
        b = 1;
        System.out.println(a);
        //        System.out.println(b);
    }

    public static void main(String[] args) {
        new TestStaticVariable();
    }
}
