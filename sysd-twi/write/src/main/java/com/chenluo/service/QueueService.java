package com.chenluo.service;

import com.chenluo.config.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class QueueService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final KafkaTemplate<String, String> kafkaTemplate;

    public QueueService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMsg(String key, String msg) {
        CompletableFuture<SendResult<String, String>> result = kafkaTemplate.send(KafkaConfig.POST_TOPIC_ID, key, msg);
        result.whenComplete((res, err) -> {
            if (err != null) {
                logger.error("failed", err);
            } else {
                logger.info("success: {}", res);
            }
        });
        return true;
    }

}
