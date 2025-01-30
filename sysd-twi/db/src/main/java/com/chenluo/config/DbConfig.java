package com.chenluo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class DbConfig {
    @Value("${REDIS_URL:localhost}")
    private String redisUrl;
    @Value("${REDIS_PORT:6379}")
    private int redisPort;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(4);
        config.setJmxEnabled(false);
        return new JedisPool(config, redisUrl, redisPort);
    }

}
