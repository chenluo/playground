package com.chenluo.cache.replacepolicy;

public interface ReplacePolicy<K> {
    K tryPutKey(K key);
}
