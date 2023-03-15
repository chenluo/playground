package com.chenluo.scheduled;

import com.chenluo.jpa.dto.Account;
import com.chenluo.jpa.repo.AccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class ScheduledTasks {
    private final static Random random = new Random();
    private static final AtomicInteger scheduledCount = new AtomicInteger();
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final WebClient webClient = WebClient.create("http://localhost:8081");
    @Resource
    private final AccountRepo accountRepo;
    @Value("${timeout:0}")
    private int timeout;
    @Value("${timeout2:0}")
    private int timeout2;

    public ScheduledTasks(JdbcTemplate jdbcTemplate, AccountRepo accountRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountRepo = accountRepo;
    }

    @Scheduled(cron = "* 1 * * * *")
    public void saveJpaData() {
        scheduledCount.incrementAndGet();
        logger.info("[saveJpaData] {} times", scheduledCount);

        for (int i = 0; i < 10000; i++) {
            CompletableFuture.runAsync(() -> {
                accountRepo.save(new Account(nextRandomLocCode(), null, null));
            });
        }
    }


    @Scheduled(cron = "* 1 * * * *")
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


    @Scheduled(cron = "*/10 * * * * *")
    public void mockMessage() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch("insert messages");
        stopWatch.start();
        String prefix = nextRandomLocCode();
        String insertSql = "insert into message(" +
                "message_no, message_type, client_id, state, data_process_no, " +
                "request_no, detail, sort_date_time)" + "values (?, ?, ?, ?, ?, ?, ?, ?);";
        Function<String, String> f = (String i) -> "0".repeat(5 - i.length()) + i;
        CompletableFuture<Void> voidCompletableFuture = null;
        for (int i = 0; i < 50000; i++) {
            int finalI = i;
            voidCompletableFuture = CompletableFuture.runAsync(
                    () -> jdbcTemplate.execute(insertSql, new PreparedStatementCallback<Boolean>() {
                        @Override
                        public Boolean doInPreparedStatement(PreparedStatement ps)
                                throws SQLException, DataAccessException {
                            ps.setString(1, prefix + f.apply(String.valueOf(finalI)));
                            ps.setString(2, "message_type_" + random.nextInt(10));
                            ps.setInt(3, random.nextInt(64));
                            ps.setInt(4, random.nextInt(10));
                            ps.setInt(5, 1);
                            ps.setInt(6, 1);
                            String content = Stream.generate(() -> nextRandomLocCode())
                                    .limit(random.nextInt(10)).reduce("", (s1, s2) -> s1 + s2);
                            ps.setString(7, "{\"content\":\"" + content + "\"}");

                            ps.setObject(8, LocalDateTime.now());
                            return ps.execute();
                        }
                    }));
        }
        voidCompletableFuture.get();
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
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
