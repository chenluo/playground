package com.chenluo.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaDupMsgCase {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Properties adminProps = new Properties();
        adminProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        AdminClient adminClient = AdminClient.create(adminProps);

        ListTopicsResult topics = adminClient.listTopics();
        if (topics.names().get().contains(KafkaConstants.TOPIC)) {
            adminClient.deleteTopics(Collections.singleton(KafkaConstants.TOPIC));
        }
        NewTopic newTopic = new NewTopic(KafkaConstants.TOPIC, 8, (short) 1);
        CreateTopicsResult topicResult = adminClient.createTopics(Collections.singleton(newTopic));
        topicResult.all();

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        int totalMsgCount = 80;
        MessageProducer producer = new MessageProducer();
        int key = 0;
        while (key < totalMsgCount) {
            producer.getProducer()
                    .send(new ProducerRecord<>(KafkaConstants.TOPIC, String.valueOf(key),
                            String.valueOf(key)));
            key++;
        }

        Map<Integer, Integer> consumedBy1 = new ConcurrentHashMap<Integer, Integer>();
        Map<Integer, Integer> consumedBy2 = new ConcurrentHashMap<Integer, Integer>();
        MessageConsumer consumer1 = new MessageConsumer();
        MessageConsumer consumer2 = new MessageConsumer();
        AtomicBoolean stopConsumer1 = new AtomicBoolean();

        // consume but not commit
        executorService.execute(() -> {
            while (!stopConsumer1.get()) {
                for (ConsumerRecord<String, String> msg : consumer1.getConsumer()
                        .poll(Duration.ofMillis(100))) {
                    Integer kk = Integer.valueOf(msg.key());
                    consumedBy1.compute(kk, (k, v) -> {
                        if (v == null) {
                            return 1;
                        }
                        return v + 1;
                    });
                }
            }
            System.out.println("consumer1 exit");
            consumer1.getConsumer().close();
        });

        AtomicBoolean stopConsumer2 = new AtomicBoolean();
        executorService.execute(() -> {
            System.out.printf("consume2 is running on %s%n", Thread.currentThread().getName());
            try {
                while (!stopConsumer2.get()) {
                    for (ConsumerRecord<String, String> msg : consumer2.getConsumer()
                            .poll(Duration.ofMillis(100))) {
                        Integer kk = Integer.valueOf(msg.key());
                        consumedBy2.compute(kk, (k, v) -> {
                            if (v == null) {
                                return 1;
                            }
                            System.out.println(kk);
                            return v + 1;
                        });
                    }
                    consumer2.getConsumer().commitSync();
                }
            } finally {
                consumer2.getConsumer().close();
            }
        });

        try {
            while (true) {
                Thread.sleep(100);
                Set<Integer> consumed = new HashSet<>();
                consumed.addAll(consumedBy1.keySet());
                consumed.addAll(consumedBy2.keySet());
                if (consumed.size() == totalMsgCount) {
                    System.out.println("all msg consumed");
                    stopConsumer1.set(true);
                    System.out.println(consumedBy1);
                    System.out.println(consumedBy2);
                    Set<Integer> s = new HashSet<>(consumedBy1.keySet());
                    s.retainAll(new HashSet<>(consumedBy2.keySet()));
                    System.out.println(s);
                    Thread.sleep(60_000); // give enough time rebalance.
                    break;
                }
            }
            ZonedDateTime afterRebalance = ZonedDateTime.now();
            while (afterRebalance.plusSeconds(30).isAfter(ZonedDateTime.now())) {
                Thread.sleep(100);
                if (consumedBy2.keySet().stream().anyMatch(consumedBy1::containsKey)) {
                    HashSet<Integer> dups = new HashSet<>(consumedBy2.keySet());
                    dups.retainAll(consumedBy1.keySet());
                    System.out.println("dups: %s".formatted(dups));
                    throw new RuntimeException("duplication");
                } else if (consumedBy2.values().stream().reduce(0, Integer::sum) == totalMsgCount) {
                    System.out.println("no duplication");
                    return;
                }
            }
            if (consumedBy2.keySet().size() != totalMsgCount) {
                throw new RuntimeException(
                        "not all message consumed by consumer2, may need wait more time");
            }
        } finally {
            stopConsumer2.set(true);
            executorService.shutdown();
            while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
            }

            adminClient.deleteTopics(Collections.singleton(KafkaConstants.TOPIC));
        }
    }
}
