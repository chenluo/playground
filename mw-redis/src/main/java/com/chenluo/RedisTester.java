package com.chenluo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;

public class RedisTester {
    private final Logger logger = LogManager.getLogger(RedisTester.class);

    public static void main(String[] args) {
        new RedisTester().run();
    }

    private void run() {

        RedissonClient client = RedissonFactory.get();
        RBitSet bitset = client.getBitSet("bitset");
        int bitIndex = 10;
        logger.info(bitset.get(bitIndex));
        bitset.set(bitIndex);
        logger.info(bitset.get(bitIndex));
        bitset.clear(bitIndex);
        logger.info(bitset.get(bitIndex));
        bitset.set(bitIndex);
        client.shutdown();
        RedissonReactiveClient reactive = client.reactive();
    }
}
