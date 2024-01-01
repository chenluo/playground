package com.chenluo.kafka.springkafka;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

// @Component
public class SpringKafkaManualCreateConsumers implements InitializingBean {
    private final KafkaProperties kafkaProperties;

    public SpringKafkaManualCreateConsumers(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
