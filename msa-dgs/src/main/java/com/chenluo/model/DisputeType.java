package com.chenluo.model;

public enum DisputeType {
    CREATE("create"),
    REMOVE("remove");

    private final String value;

    DisputeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
