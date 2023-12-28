package db;

import com.chenluo.Application;
import com.chenluo.data.dto.ConsumedMessage;
import com.chenluo.data.repo.ConsumedMessageRepository;
import com.chenluo.service.ConsumedMessageService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(classes = Application.class)
@Disabled
public class TransactionTest {
    @Autowired
    private ConsumedMessageRepository repository;
    @Autowired
    private ConsumedMessageService service;
    private ExecutorService executorService = Executors.newFixedThreadPool(32);

    @Test
    public void testMultipleTrans() throws InterruptedException {
        final UUID uuid = UUID.randomUUID();
        ConsumedMessage entity = new ConsumedMessage(0, uuid.toString(), 0, 0);

        repository.save(entity);
        AtomicInteger executed = new AtomicInteger();

        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                executed.incrementAndGet();
                service.increament(uuid);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        ConsumedMessage finalMessage = repository.findByUuid(uuid.toString());
        assert finalMessage.count == 10000 : "message count is %d rather than %d".formatted(finalMessage.count, executed.get());
    }

    @Test
    public void testMultipleTransByLock() throws InterruptedException {
        final UUID uuid = UUID.randomUUID();
        ConsumedMessage entity = new ConsumedMessage(0, uuid.toString(), 0, 0);

        repository.save(entity);
        AtomicInteger executed = new AtomicInteger();

        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                executed.incrementAndGet();
                service.increamentByLock(uuid);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        ConsumedMessage finalMessage = repository.findByUuid(uuid.toString());
        assert finalMessage.count == 10000 : "message count is %d rather than %d".formatted(finalMessage.count, executed.get());
    }
}
