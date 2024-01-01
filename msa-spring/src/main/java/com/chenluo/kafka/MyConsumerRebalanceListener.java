package com.chenluo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

public class MyConsumerRebalanceListener implements ConsumerRebalanceListener {
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        System.out.println("onPartitionRevoked");
        System.out.println(
                "[revoked] thread: %s:%s"
                        .formatted(Thread.currentThread().getName(), collectToStr(partitions)));
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.println("onPartitionsAssigned");
        System.out.println(
                "[assigned] thread: %s:%s"
                        .formatted(Thread.currentThread().getName(), collectToStr(partitions)));
    }

    private String collectToStr(Collection<TopicPartition> partitions) {
        return String.join(
                ",",
                partitions.stream().map(TopicPartition::partition).map(String::valueOf).toList());
    }
}
