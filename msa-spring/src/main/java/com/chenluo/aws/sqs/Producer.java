package com.chenluo.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.context.SmartLifecycle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

// @Component
public class Producer implements SmartLifecycle {
    private static final String QUEUE_NAME = "test-queue";
    final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public static void main(String[] args) {

        // Send multiple messages to the queue
        //        SendMessageBatchRequest send_batch_request =
        //                new SendMessageBatchRequest().withQueueUrl(queueUrl).withEntries(
        //                        new SendMessageBatchRequestEntry("msg_1", "Hello from message 1"),
        //                        new SendMessageBatchRequestEntry("msg_2",
        //                                "Hello from message 2").withDelaySeconds(10));
        //        sqs.sendMessageBatch(send_batch_request);

        // receive messages from the queue
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
                SendMessageRequest send_msg_request =
                        new SendMessageRequest().withQueueUrl(queueUrl).withMessageBody(
                                        "hello world @%s".formatted(LocalDateTime.now()
                                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                                .withDelaySeconds(5);
                sqs.sendMessage(send_msg_request);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        isRunning.set(true);
    }

    @Override
    public void stop() {
        sqs.shutdown();
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }
}
