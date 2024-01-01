package com.chenluo.db;

import com.chenluo.Application;
import com.chenluo.TestBase;
import com.chenluo.data.dto.ConsumedMessage;
import com.chenluo.data.repo.ConsumedMessageRepository;
import com.chenluo.service.ConsumedMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(classes = Application.class)
public class TransactionTest extends TestBase {
    private static final int COUNT = 10;
    @Autowired
    private ConsumedMessageRepository repository;
    @Autowired
    private ConsumedMessageService service;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Test
    public void testMultipleTrans() throws InterruptedException {
        final UUID uuid = UUID.randomUUID();
        ConsumedMessage entity = new ConsumedMessage(0, uuid.toString(), 0, 0);

        repository.save(entity);
        AtomicInteger executed = new AtomicInteger();

        for (int i = 0; i < COUNT; i++) {
            executorService.execute(() -> {
                executed.incrementAndGet();
                service.increament(uuid);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        ConsumedMessage finalMessage = repository.findByUuid(uuid.toString());
        Assertions.assertNotEquals(COUNT, finalMessage.count);
    }

    @Test
    public void testMultipleTransByLock() throws InterruptedException {
        final UUID uuid = UUID.randomUUID();
        ConsumedMessage entity = new ConsumedMessage(0, uuid.toString(), 0, 0);

        repository.save(entity);
        AtomicInteger executed = new AtomicInteger();

        for (int i = 0; i < COUNT; i++) {
            executorService.execute(() -> {
                executed.incrementAndGet();
                service.increamentByLock(uuid);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        ConsumedMessage finalMessage = repository.findByUuid(uuid.toString());
        assert finalMessage.count == COUNT : "message count is %d rather than %d".formatted(finalMessage.count, executed.get());
    }
}
