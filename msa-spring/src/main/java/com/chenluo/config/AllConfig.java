package com.chenluo.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableCaching
@Import({JpaConfig.class})
public class AllConfig {
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, String> template) {
        BackOff fixedBackOff = new FixedBackOff(100, 2);
        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(new DeadLetterPublishingRecoverer(template)
                        //                (consumerRecord, e) -> {
                        //            // logic to execute when all the retry attemps are exhausted
                        //        }
                        , fixedBackOff);
        //        errorHandler.addRetryableExceptions(SocketTimeoutException.class);
        //        errorHandler.addNotRetryableExceptions(NullPointerException.class);
        return errorHandler;
    }

    @Bean
    public RecordFilterStrategy<Object, Object> recordFilterStrategy1() {
        return new RecordFilterStrategy() {
            @Override
            public boolean filter(ConsumerRecord consumerRecord) {
                return false;
            }
        };
    }

    @Bean
    public RecordFilterStrategy<Object, Object> recordFilterStrategy2() {
        return new RecordFilterStrategy() {
            @Override
            public boolean filter(ConsumerRecord consumerRecord) {
                return false;
            }
        };
    }


}
