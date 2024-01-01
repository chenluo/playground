package com.chenluo.cache.replacepolicy;

public class LRUReplacePolicyFactory<K> implements ReplacePolicyFactory {
    int capacity;

    public LRUReplacePolicyFactory(int n) {
        capacity = n;
    }

    @Override
    public ReplacePolicy<K> generateReplacePolicy() {
        return new LRUReplacePolicy<>(this.capacity);
    }
}
