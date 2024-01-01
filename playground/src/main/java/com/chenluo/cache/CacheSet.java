package com.chenluo.cache;

import com.chenluo.cache.replacepolicy.ReplacePolicy;

public class CacheSet<K, V> {
    CacheEntry<K, V>[] cacheEntries;
    ReplacePolicy<K> replacePolicy;

    public CacheSet(int N, ReplacePolicy<K> replacePolicy) {
        cacheEntries = new CacheEntry[N];
        this.replacePolicy = replacePolicy;
    }

    public void put(K key, V val) {
        if (null == key) {
            return;
        }
        K deleteCandidate = replacePolicy.tryPutKey(key);
        if (null != deleteCandidate) {
            for (CacheEntry<K, V> cacheEntry : cacheEntries) {
                if (cacheEntry.key.equals(deleteCandidate)) {
                    cacheEntry.key = key;
                    cacheEntry.value = val;
                    return;
                }
            }
        }
        for (int i = 0; i < cacheEntries.length; i++) {
            if (cacheEntries[i] == null) {
                cacheEntries[i] = new CacheEntry<>(key, val);
                break;
            }
        }
        return;
    }

    public V get(K key) {
        for (int i = 0; i < cacheEntries.length; i++) {
            if (cacheEntries[i] != null && cacheEntries[i].key.equals(key)) {
                return cacheEntries[i].value;
            }
        }
        return null;
    }
}
