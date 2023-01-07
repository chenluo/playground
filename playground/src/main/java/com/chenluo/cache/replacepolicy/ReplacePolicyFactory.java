package com.chenluo.cache.replacepolicy;

public interface ReplacePolicyFactory<K> {
    ReplacePolicy<K> generateReplacePolicy();
}
