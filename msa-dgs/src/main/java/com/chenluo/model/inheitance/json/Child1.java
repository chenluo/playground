package com.chenluo.model.inheitance.json;

public class Child1 extends Parent {
    private String child1Attr;

    public Child1(String attr, String child1Attr) {
        super(attr);
        this.child1Attr = child1Attr;
    }

    public Child1() {}

    public String getChild1Attr() {
        return child1Attr;
    }

    public void setChild1Attr(String child1Attr) {
        this.child1Attr = child1Attr;
    }
}
