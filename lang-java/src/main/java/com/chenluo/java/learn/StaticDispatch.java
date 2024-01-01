package com.chenluo.java.learn;

public class StaticDispatch {
    public static void main(String[] args) {
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayHello((Human) new Man());
        staticDispatch.sayHello((Human) new Woman());
    }

    public void sayHello(Human human) {
        System.out.println("hello human");
    }

    public void sayHello(Man man) {
        System.out.println("hello man");
    }

    public void sayHello(Woman woman) {
        System.out.println("hello woman");
    }

    abstract static class Human {
    }

    static class Man extends Human {
    }

    static class Woman extends Human {
    }
}
