package com.chenluo.scheduled;

import com.chenluo.kafka.MessageConsumer;
import com.chenluo.kafka.MessageProducer;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;

@Component
public class KafkaScheduler implements InitializingBean {
    private final Logger logger = LogManager.getLogger(KafkaScheduler.class);
    private final MessageProducer messageProducer;
    private final MessageConsumer messageConsumer;

    public KafkaScheduler(MessageProducer messageProducer, MessageConsumer messageConsumer) {
        this.messageProducer = messageProducer;
        this.messageConsumer = messageConsumer;
    }

    @Scheduled(fixedRate = 1000)
    public void produceMessage() {
        logger.info("producing");
        for (int i = 0; i < 1_000; i++) {
            Future<RecordMetadata> send = messageProducer.getProducer()
                    .send(new ProducerRecord<>("test-topic", UUID.randomUUID().toString(),
                                    String.valueOf(ZonedDateTime.now().toInstant().toEpochMilli())),
                            new Callback() {
                                @Override
                                public void onCompletion(RecordMetadata metadata,
                                                         Exception exception) {
                                    if (exception != null) {
                                        logger.error("failed to send message.", exception);
                                    }
                                }
                            });
        }
        logger.info("produce done");
    }

    @Scheduled(fixedRate = 1000)
    public void consumeMessage() {
        logger.info("consuming");
        int i = 0;
        int delayed = 0;
        long now = ZonedDateTime.now().toInstant().toEpochMilli();
        for (ConsumerRecord<String, String> message : messageConsumer.getConsumer()
                .poll(Duration.ofMillis(500))) {
            if (now - Long.parseLong(message.value()) > 1100) {
                //                logger.info("received message: key = {}, value={},
                //                partition={}, offset={}",
                //                        message.key(), message.value(), message.partition(),
                //                        message.offset());
                delayed++;
            }
            i++;
            //            messageConsumer.getConsumer().commitSync(Collections.singletonMap(
            //                    new TopicPartition(message.topic(), message.partition()),
            //                    new OffsetAndMetadata(message.offset() + 1)));
        }
        messageConsumer.getConsumer().commitSync();
        logger.info("consume done: {} messages consumed. {} delayed", i, delayed);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.50.42:9092");
        AdminClient adminClient = AdminClient.create(props);
        ListTopicsResult topics = adminClient.listTopics();
        if (topics.names().get().contains("test-topic")) {
            return;
        }
        NewTopic newTopic = new NewTopic("test-topic", 3, (short) 1);
        CreateTopicsResult topicResult = adminClient.createTopics(Collections.singleton(newTopic));
        topicResult.all();
        adminClient.close();
    }
}
