package com.chenluo.kafka.springkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

// @Component
public class SpringKafkaAnnotationConsumers {
    private final Random random = new Random();
    private final AtomicInteger i = new AtomicInteger();

    //    @RetryableTopic(attempts = "2", topicSuffixingStrategy =
    //            TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE, backoff = @Backoff(delay = 1000,
    //            multiplier = 2.0), autoCreateTopics = "false"
    //            , exclude = {Exception.class,}
    //    )
    //    @KafkaListener(topics = "test-topic", groupId = "spring-kafka-nb", concurrency = "1")
    public void consumeNonBlockRetry(ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.println(Thread.currentThread().getName() + " : " + record.value());
        if (i.incrementAndGet() % 10 == -1) { // always exception.
            ack.acknowledge();
        } else {
            throw new RuntimeException(record.value());
        }
    }

    @KafkaListener(
            topics = "test-topic",
            groupId = "spring-kafka-b",
            concurrency = "1",
            filter = "recordFilterStrategy1")
    //    public void consumeBlockRetry(ConsumerRecord<String, String> record, Acknowledgment ack)
    public void consumeBlockRetry(ConsumerRecord<String, String> record)
            throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " : " + record.offset());
        //        Thread.sleep(1000);
        //        if (i.get() > 8) {
        //            while (true) {
        //                Thread.sleep(1000);
        //                System.out.println(i.get());
        //            }
        //        }
        //        i.incrementAndGet();
    }

    //    @DltHandler
    public void dlt(
            String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.ORIGINAL_TOPIC) String originTopic) {
        System.out.println("dlt %s, %s".formatted(message, topic));
    }
}
