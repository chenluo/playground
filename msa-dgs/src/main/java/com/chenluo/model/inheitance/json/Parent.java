package com.chenluo.model.inheitance.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "__type")
@JsonSubTypes(
        value = {
            @JsonSubTypes.Type(value = Child1.class, name = "child1"),
            @JsonSubTypes.Type(value = Child2.class, name = "child2")
        })
public abstract class Parent {
    private String attr;

    public Parent(String attr) {
        this.attr = attr;
    }

    public Parent() {}

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
