package com.chenluo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheableServiceImplTest {
    @Autowired private CacheableServiceImpl cacheableService;

    @Test
    void get() {
        String key = "key";
        String val = cacheableService.get(key);
        String val2 = cacheableService.get(key);
        Assertions.assertEquals(val, val2);
    }

    @Test
    void getInLoop() {
        String key = "key2";
        String val1 = "";
        String val2 = "";
        for (int i = 0; i < 10; i++) {
            if (val1.isEmpty()) {
                val1 = cacheableService.get(key);
                continue;
            }
            val2 = cacheableService.get(key);
            Assertions.assertEquals(val1, val2);
        }
    }
}
