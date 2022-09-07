package com.chenluo.java;

public class SubClass extends SuperClass {
    private PrintClass b = new PrintClass("sub variable init");

    public SubClass() {
        System.out.println("Sub class contor");
    }


    public static void main(String[] args) {
        new SubClass();
    }
}
