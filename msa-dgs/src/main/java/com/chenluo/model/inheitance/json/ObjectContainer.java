package com.chenluo.model.inheitance.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ObjectContainer {
    List<Parent> elements;

    @JsonCreator
    public ObjectContainer(@JsonProperty("elements") List<Parent> elements) {
        this.elements = elements;
    }

    public List<Parent> getElements() {
        return elements;
    }

    public void setElements(List<Parent> elements) {
        this.elements = elements;
    }
}
