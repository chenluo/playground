package com.chenluo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RBitSet;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;

import java.time.Duration;

public class RedisTester {
    private final Logger logger = LogManager.getLogger(RedisTester.class);
    private final RedissonClient client = RedissonFactory.get();

    public static void main(String[] args) throws InterruptedException {
        RedisTester redisTester = new RedisTester();
        //        redisTester.run();
        redisTester.testRedisDataStructure();
        redisTester.client.shutdown();
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

    private void testRedisDataStructure() throws InterruptedException {
        // string
        String key = "string-key";
        RBucket<String> bucket = client.getBucket(key);
        logger.info("{}, {}", key, bucket.get());
        String value1 = "string-val";
        bucket.set(value1);
        logger.info("{}, {}", key, bucket.get());
        bucket.expire(Duration.ofSeconds(1));
        Thread.sleep(Duration.ofSeconds(2));
        logger.info("{}, {}", key, bucket.get());
    }


}
