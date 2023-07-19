package com.chenluo.scheduled;

import com.chenluo.data.dto.ConsumedMessage;
import com.chenluo.data.repo.ConsumedMessageRepository;
import com.chenluo.kafka.MessageConsumer;
import com.chenluo.kafka.MessageProducer;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class KafkaScheduler implements InitializingBean {
    private final Logger logger = LogManager.getLogger(KafkaScheduler.class);
    private final MessageProducer messageProducer;
    private final MessageConsumer messageConsumer;
    private final ConsumedMessageRepository consumedMessageRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final Random random = new Random(1);

    public KafkaScheduler(MessageProducer messageProducer, MessageConsumer messageConsumer,
                          ConsumedMessageRepository consumedMessageRepository) {
        this.messageProducer = messageProducer;
        this.messageConsumer = messageConsumer;
        this.consumedMessageRepository = consumedMessageRepository;
    }

//    @Scheduled(fixedRate = 1000)
    public void produceMessage() {
        logger.info("producing");
        for (int i = 0; i < 100; i++) {
            sendMessage(UUID.randomUUID().toString(),
                    String.valueOf(ZonedDateTime.now().toInstant().toEpochMilli()));
        }
        logger.info("produce done");
    }

    private void sendMessage(String key, String value) {
        Future<RecordMetadata> send = messageProducer.getProducer()
                .send(new ProducerRecord<>("test-topic", key, value), (metadata, exception) -> {
                    if (exception != null) {
                        logger.error("failed to send message.", exception);
                    }
                });
    }

    //    @Scheduled(fixedRate = 1000)
    public void consumeMessage() {
        logger.info("consuming");
        int i = 0;
        int delayed = 0;
        int resent = 0;
        long now = ZonedDateTime.now().toInstant().toEpochMilli();
        List<ConsumedMessage> insertMessage = new ArrayList<>();
        List<ConsumedMessage> updateMessages = new ArrayList<>();
        Map<String, String> resendMessages = new HashMap<>();
        boolean exception = false;

        try {
            for (ConsumerRecord<String, String> message : messageConsumer.getConsumer()
                    .poll(Duration.ofMillis(500))) {
                ConsumedMessage targetMessage;
                ConsumedMessage consumedMessage =
                        consumedMessageRepository.findByUuid(message.key());
                logger.info("consuming record: {}", message.key());
                if (consumedMessage != null && consumedMessage.success == 1) {
                    logger.error("re-consumed message id: %s, offset: %s".formatted(message.key(),
                            message.offset()));
                }
                targetMessage = consumedMessage;
                if (targetMessage == null) {
                    targetMessage = new ConsumedMessage(0, message.key(), 1, 1);
                    insertMessage.add(targetMessage);
                } else {
                    targetMessage.count++;
                    updateMessages.add(targetMessage);
                }

                if (now - Long.parseLong(message.value()) > 1100) {
                    delayed++;
                }
                if (random.nextInt(100) >= 1) {
                    // consume success
                    targetMessage.success = 1;
                } else {
                    resent++;
                    // consume fail
                    targetMessage.success = 0;
                    // resend message
                    resendMessages.put(message.key(), message.value());
                }
                // commit 1 by 1
                //                messageConsumer.getConsumer().commitSync(Collections.singletonMap(
                //                        new TopicPartition(message.topic(), message.partition()),
                //                        new OffsetAndMetadata(message.offset() + 1)));
            }
        } catch (Exception e) {
            logger.error("{}", e);
            exception = true;
        } finally {
            if (exception) {
                return;
            }
            //            messageConsumer.getConsumer().commitAsync(new OffsetCommitCallback() {
            //                @Override
            //                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets,
            //                                       Exception exception) {
            //                    if (exception == null) {
            //                        logger.info("success commitAsync");
            //                    } else {
            //                        logger.warn("failed to commitAsync");
            //                    }
            //                }
            //            });
            consumedMessageRepository.saveAll(insertMessage);
            updateMessages.forEach(consumedMessageRepository::save);
            resendMessages.forEach((k, v) -> {
                executorService.submit(() -> {
                    logger.warn("resend message: {}", k);
                    sendMessage(k, v);
                });
            });
            logger.info("consume done: {} messages consumed. {} delayed. {} resent.",
                    insertMessage.size(), delayed, resent);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
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
