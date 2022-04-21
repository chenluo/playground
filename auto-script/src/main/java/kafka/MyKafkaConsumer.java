package kafka;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyKafkaConsumer {
    private KafkaConsumer<Integer, String> consumer;
    private String groupId;
    private ExecutorService pool;
    private final Logger logger = LoggerFactory.getLogger(MyKafkaConsumer.class);

    public static void main(String[] args) {
//        myKafkaConsumer.consumeAndEcho();
        for (int i = 0; i < 10; i++) {
            MyKafkaConsumer myKafkaConsumer = new MyKafkaConsumer("group-" + i);
            myKafkaConsumer.run();
        }
    }

    public MyKafkaConsumer(String groupId) {
        this.groupId = groupId;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, MyKafkaConfig.BOOTSTRAP_SERVER_URL);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        instanceId.ifPresent(id -> props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, id));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        if (readCommitted) {
//            props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
//        }
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(props);

        ThreadFactory factory = new BasicThreadFactory.Builder().namingPattern("kafka-consumer-"+groupId+"-%d")
                .daemon(false).build();
        this.pool = new ThreadPoolExecutor(0, 1, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), factory);
    }

    public void run() {
        logger.info("starting.");
        List<Future> resultList = new ArrayList<>();
        consumer.subscribe(Collections.singletonList(MyKafkaConfig.TOPIC_NAME));

        resultList.add(pool.submit(this::consume));
        logger.info("started.");
    }

    public void consume() {
        int consumed = 0;
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(10));
            if (records.isEmpty()) {
                break;
            }
            AtomicInteger temp = new AtomicInteger();
            records.iterator().forEachRemaining(record -> {
                logger.info((this.groupId + " received message : from partition " + record.partition() + ", (" + record.key() + ", " + record.value() + ") at offset " + record.offset()));
                temp.getAndIncrement();
            });
            if (temp.get() == 0) {
                break;
            }
            consumed+=temp.get();
        }
        logger.info(Thread.currentThread().getName()+ " consumed " + consumed + " messages.");
    }
}
