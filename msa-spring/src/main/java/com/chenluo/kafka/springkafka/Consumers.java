package com.chenluo.kafka.springkafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumers {
    @KafkaListener(topics = "test-topic", groupId = "spring-kafka", concurrency = "3")
    public void consume(String message) {
        System.out.println(Thread.currentThread().getName() + " : " + message);
    }
}
