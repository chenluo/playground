package com.chenluo.model.inheitance.json;

public class Child2 extends Parent {
    private String child2Attr;

    public Child2(String attr, String child2Attr) {
        super(attr);
        this.child2Attr = child2Attr;
    }

    public Child2() {
        super();
    }

    public String getChild2Attr() {
        return child2Attr;
    }

    public void setChild2Attr(String child2Attr) {
        this.child2Attr = child2Attr;
    }
}
