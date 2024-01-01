package com.chenluo.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

// @Component
public class MessageConsumer {
    private final KafkaConsumer<String, String> consumer;

    public MessageConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50000);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 6000);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30_000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 5_000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singleton("test-topic"), new MyConsumerRebalanceListener());
    }

    public MessageConsumer(Properties props, String topic) {
        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singleton(topic), new MyConsumerRebalanceListener());
    }

    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }
}
