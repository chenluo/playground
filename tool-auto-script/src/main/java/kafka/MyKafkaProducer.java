package kafka;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class MyKafkaProducer {
    private KafkaProducer<Integer, String> producer;
    private ExecutorService threadPool;
    private int messageCount;
    private int threadCount;
    private Logger logger = LoggerFactory.getLogger(MyKafkaProducer.class);

    public MyKafkaProducer(int messageCount, int threadCount) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, MyKafkaConfig.BOOTSTRAP_SERVER_URL);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        properties.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        producer = new KafkaProducer<>(properties);
        threadPool =
                new ThreadPoolExecutor(
                        0,
                        threadCount,
                        10,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(10),
                        new BasicThreadFactory.Builder()
                                .namingPattern("kafka-producer-%d")
                                .build());
        this.messageCount = messageCount;
        this.threadCount = threadCount;
    }

    public static void main(String[] args) {
        MyKafkaProducer myKafkaProducer = new MyKafkaProducer(1000, 10);
        myKafkaProducer.run();
    }

    public void run() {
        logger.info("start to run producer");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Future> results = new ArrayList<>();
        for (int i = 0; i < this.threadCount; i++) {
            results.add(threadPool.submit(this::produceAllMessage));
        }
        for (Future f : results) {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        producer.close();
        this.threadPool.shutdown();
        while (true) {
            try {
                if (this.threadPool.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("finish produce message and close producer");
        stopWatch.stop();
        //        logger.info(stopWatch.getMessage());
    }

    private void produceAllMessage() {
        logger.info("start produce message.");
        for (int i = 0; i < messageCount; i++) {
            produceMessage(i, "Message_" + i);
        }
        logger.info("finish produce message.");
    }

    private void produceMessage(int mesKey, String mes) {
        producer.send(new ProducerRecord<>(MyKafkaConfig.TOPIC_NAME, mesKey, mes));
    }
}
