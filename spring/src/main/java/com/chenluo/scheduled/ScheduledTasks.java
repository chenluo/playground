package com.chenluo.scheduled;

import com.chenluo.jpa.dto.Account;
import com.chenluo.jpa.repo.AccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduledTasks {
    private static AtomicInteger scheduledCount = new AtomicInteger();

    private final static Random random = new Random();

    private final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final WebClient webClient = WebClient.create("http://localhost:8081");

    @Value("${timeout:0}")
    private int timeout;

    @Value("${timeout2:0}")
    private int timeout2;

    @Resource
    private final AccountRepo accountRepo;

    public ScheduledTasks(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    //    @Scheduled(cron = "* 1 * * * *")
    public void saveJpaData() {
        scheduledCount.incrementAndGet();
        logger.info("[saveJpaData] {} times", scheduledCount);

        for (int i = 0; i < 10000; i++) {
            CompletableFuture.runAsync(() -> {
                accountRepo.save(new Account(nextRandomLocCode(), null, null));
            });
        }
    }


    //    @Scheduled(cron = "* 1 * * * *")
    public void getJpaData() {
        for (int i = 0; i < 1; i++) {
            List<Account> accountsByCol1 = accountRepo.findAccountsByCol1(nextRandomLocCode());
            for (Account account : accountsByCol1) {
                logger.info("[getJpaData] found: {}", account);
            }
        }
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void printTimeout() {
        logger.info("timeout is {}", timeout);
        logger.info("timeout2 is {}", timeout2);
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void webClient() {
        logger.info("webclient get result {}",
                webClient.get().uri("/main/tessst").retrieve().bodyToMono(Boolean.class).block());
    }


    private String nextRandomLocCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char c = (char) ('A' + random.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }

}
