package com.chenluo.kafka;

import com.chenluo.config.KafkaConsumerConfig;
import com.chenluo.service.FanoutService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PostConsumer {
    private final FanoutService fanoutService;

    public PostConsumer(FanoutService fanoutService) {
        this.fanoutService = fanoutService;
    }

    @KafkaListener(topics = KafkaConsumerConfig.POST_TOPIC_ID, concurrency = "3")
    public void listenGroupFoo(@Payload String tid, @Header(KafkaHeaders.RECEIVED_KEY) String uid, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        fanoutService.fanout(uid, tid);
    }
}
