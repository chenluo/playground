package com.chenluo.scheduled;

import com.chenluo.jpa.dto.Account;
import com.chenluo.jpa.repo.AccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduledTasks {
    private static AtomicInteger scheduledCount = new AtomicInteger();

    private final static Random random = new Random();

    private final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Resource
    private final AccountRepo accountRepo;

    public ScheduledTasks(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Scheduled(cron = "* */1 * * * *")
    public void saveJpaData() {
        scheduledCount.incrementAndGet();
        logger.info("[saveJpaData] {} times", scheduledCount);

        for (int i = 0; i < 10000; i++) {
            accountRepo.save(new Account(nextRandomLocCode(), null, null));
        }
    }


    @Scheduled(cron = "*/1 * * * * *")
    public void getJpaData() {
        for (int i = 0; i < 1; i++) {
            List<Account> accountsByCol1 = accountRepo.findAccountsByCol1(nextRandomLocCode());
            for (Account account : accountsByCol1) {
                logger.info("[getJpaData] found: {}", account);
            }
        }
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
