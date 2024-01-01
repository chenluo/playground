package com.chenluo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.*;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamReadArgs;

import java.time.Duration;
import java.util.Comparator;

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
        stringBucket();
        objectBucket();
        redisList();
        redisSet();
        redisHashMap();
        redisZSet();
        redisStream();
    }

    private void redisStream() {
        String key = "stream-key";
        RStream<String, MyRedisObj> stream = client.getStream(key);
        stream.delete();
        String groupName = "group1";
        stream.createGroup(groupName);
        logger.info("groups :{}", stream.listGroups().size());
        StreamMessageId firstMsgId =
                stream.add(StreamAddArgs.entry("key_0", new MyRedisObj("key_0", 0, 0)));
        logger.info("firstMsgId: {}", firstMsgId);
        //        String consumerName = "consumerName";
        //        stream.createConsumer(groupName, consumerName);
        logger.info("read first msg: {}",
                stream.read(StreamReadArgs.greaterThan(new StreamMessageId(0)).count(1)));
    }

    private void redisZSet() {
        String key = "zset-key";
        RSortedSet<MyRedisObj> sortedSet = client.getSortedSet(key);
        sortedSet.delete();
        boolean success = sortedSet.trySetComparator(new Comparator<MyRedisObj>() {
            @Override
            public int compare(MyRedisObj o1, MyRedisObj o2) {
                return Integer.compare(o1.integer(), o2.integer());
            }
        });
        logger.info("set comparator: {}", success);
        for (int i = 0; i < 10; i++) {
            sortedSet.add(new MyRedisObj("key_" + i, i, 10 - i));
        }
        logger.info("{}: {}", key, sortedSet);
        success = sortedSet.trySetComparator(new Comparator<MyRedisObj>() {
            @Override
            public int compare(MyRedisObj o1, MyRedisObj o2) {
                return Integer.compare(o1.i(), o2.i());
            }
        });
        logger.info("set comparator: {}", success);
        logger.info("{}: {}", key, sortedSet);
        sortedSet.remove(new MyRedisObj("key_0", 0, 10));
        sortedSet.add(new MyRedisObj("key_0", -1, 10));
        logger.info("{}: {}", key, sortedSet);
        //        SortedSet<MyRedisObj> subset = sortedSet.subSet(new MyRedisObj("key_0", -1, 10),
        //                new MyRedisObj("key_2", 2, 8));
        //        logger.info("{}: {}", key, subset);
    }

    private void redisHashMap() {
        String key = "hash-key";
        RMap<String, MyRedisObj> map = client.getMap(key);
        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), new MyRedisObj("key_" + i, i, i));
        }
        map.put("0", new MyRedisObj("key_00", 0, 0));
        logger.info("{}: {}", "0", map.get("0"));
    }

    private void redisSet() {
        String key = "set-key";
        RSet<MyRedisObj> set = client.getSet(key);
        for (int i = 0; i < 10; i++) {
            set.add(new MyRedisObj("key_" + i, i, i));
        }
        set.add(new MyRedisObj("key_" + 0, 0, 0));
        logger.info("{}: length={}", key, client.getSet(key).size());
    }

    private void redisList() {
        String key = "list-key";
        RList<MyRedisObj> list = client.getList(key);
        for (int i = 0; i < 10; i++) {
            list.add(new MyRedisObj("key_" + i, i, i));
        }
        logger.info("{}: length={}", key, client.getList(key).size());
        for (int i = 0; i < list.size(); i++) {
            logger.info("{}", list.get(i));
        }
    }

    private void objectBucket() {
        String objectKey = "object-key";
        RBucket<MyRedisObj> bucket = client.getBucket(objectKey);
        bucket.set(new MyRedisObj("key", 1, 1));
        logger.info("{}, {}", objectKey, bucket.get());
    }

    private void stringBucket() throws InterruptedException {
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
