package com.chenluo;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonFactory {
    public static RedissonClient get() {
        Config config = new Config();
        // use "rediss://" for SSL connection
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient client = Redisson.create(config);
        return client;
    }
}
