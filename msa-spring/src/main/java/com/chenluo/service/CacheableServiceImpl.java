package com.chenluo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CacheableServiceImpl {
    @Cacheable(value = "cache1", key = "#key")
    public String get(String key) {
        return UUID.randomUUID().toString();
    }
}
