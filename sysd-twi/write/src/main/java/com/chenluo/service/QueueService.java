package com.chenluo.service;

import com.chenluo.config.KafkaConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class QueueService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public QueueService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMsg(String key, String msg) {
        CompletableFuture<SendResult<String, String>> result = kafkaTemplate.send(KafkaConfig.POST_TOPIC_ID, key, msg);
        result.whenComplete((res, err) -> {
            if (err != null) {
                System.out.printf("failed: %s%n", err.getMessage());
            } else {
                System.out.printf("success: %s%n", res);
            }
        });
        return true;
    }

}
