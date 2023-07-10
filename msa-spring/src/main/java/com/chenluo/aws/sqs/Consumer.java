package com.chenluo.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import org.springframework.context.SmartLifecycle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

//@Component
public class Consumer implements SmartLifecycle {

    private static final String QUEUE_NAME = "test-queue";
    final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public static void main(String[] args) {
    }

    @Override
    public void start() {
        try {
            CreateQueueResult create_result = sqs.createQueue(QUEUE_NAME);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
        executorService.execute(() -> {
            while (true) {
                List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
                // delete messages from the queue
                for (Message m : messages) {
                    System.out.println(m.getBody());
                    sqs.deleteMessage(queueUrl, m.getReceiptHandle());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void stop() {
        sqs.shutdown();

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
