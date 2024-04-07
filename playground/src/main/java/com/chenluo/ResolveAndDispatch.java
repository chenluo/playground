package com.chenluo;

public class ResolveAndDispatch {
    public static void main(String[] args) {
        ResolveAndDispatch sut = new ResolveAndDispatch();
        Parent param = new Child1();
        sut.invokeOn(param);
        Child1 child1 = new Child1();
        sut.invokeOn(child1);
        //        lookup().findSpecial()
    }

    static class Parent {}

    static class Child1 extends Parent {}

    static class Child2 extends Parent {}

    public void invokeOn(Parent param) {
        System.out.println("parent");
    }

    public void invokeOn(Child1 param) {
        System.out.println("child1");
    }

    public void invokeOn(Child2 param) {
        System.out.println("child2");
    }
}
