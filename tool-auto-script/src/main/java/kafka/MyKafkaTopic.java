package kafka;

import org.apache.kafka.clients.admin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyKafkaTopic {
    private Admin admin;
    private Logger logger = LoggerFactory.getLogger(MyKafkaTopic.class);

    public MyKafkaTopic() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                MyKafkaConfig.BOOTSTRAP_SERVER_URL);

        admin = Admin.create(properties);
    }

    public static void main(String[] args) {
        MyKafkaTopic kafkaTopic = new MyKafkaTopic();
        try {
            if (args[0].equals("create")) {
                kafkaTopic.createTopic(MyKafkaConfig.TOPIC_NAME, (short) 3);
            } else if (args[0].equals("delete")) {
                kafkaTopic.deleteTopic(MyKafkaConfig.TOPIC_NAME);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createTopic(String topicName, short partition)
            throws ExecutionException, InterruptedException {
        List<NewTopic> topicList = new ArrayList<>();
        topicList.add(new NewTopic(topicName, partition, partition));

        CreateTopicsOptions options = new CreateTopicsOptions();
        CreateTopicsResult result = admin.createTopics(topicList);
        int tryCount = 10;
        boolean success = false;
        while (tryCount > 0 && !success) {
            tryCount--;
            try {
                result.all().get(1000, TimeUnit.MILLISECONDS);
                success = true;
            } catch (TimeoutException e) {
                logger.warn("timeout");
            }
        }
        if (!success) {
            logger.error("failed to create topic");
        } else {
            logger.info("created.");
        }
    }

    public void deleteTopic(String topicName) throws ExecutionException, InterruptedException {
        List<String> topicList = new ArrayList<>();
        topicList.add(topicName);

        DeleteTopicsOptions deleteTopicsOptions = new DeleteTopicsOptions();
        DeleteTopicsResult result = admin.deleteTopics(topicList);
        int tryCount = 10;
        boolean success = false;
        while (tryCount > 0 && !success) {
            tryCount--;
            try {
                result.all().get(1000, TimeUnit.MILLISECONDS);
                success = true;
            } catch (TimeoutException e) {
                logger.warn("timeout");
            }
        }
        if (!success) {
            logger.error("failed to delete topic");
        } else {
            logger.info("deleted.");
        }
    }
}
