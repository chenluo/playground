package com.chenluo.scheduled;

import com.chenluo.service.MyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

//@Component
public class MysqlScheduler {
    private final Logger logger = LogManager.getLogger(MysqlScheduler.class);
    private final ThreadPoolExecutor insertPool =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
    private final ThreadPoolExecutor selectPool =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(50);

    private final AtomicInteger insertCounter = new AtomicInteger();
    private final AtomicInteger selectCounter = new AtomicInteger();

    private final MyService myService;

    public MysqlScheduler(MyService myService) {
        this.myService = myService;
    }

    @Scheduled(fixedRate = 1000)
    public void insertMessage() {
        logger.info("inserting: completed count = {}", insertPool.getCompletedTaskCount());
        logger.info("inserting: op per s {}",
                insertPool.getCompletedTaskCount() / insertCounter.incrementAndGet());
        logger.info("inserting: queued tasks = {}", insertPool.getQueue().size());
        for (int i = 0; i < 10_000; i++) {
            insertPool.submit(myService::insertMessage);
        }
        logger.info("insert done");
    }

    @Scheduled(fixedRate = 1000)
    public void queryMessage() {
        logger.info("querying: completed count = {}", selectPool.getCompletedTaskCount());
        logger.info("querying : op per s {}",
                selectPool.getCompletedTaskCount() / selectCounter.incrementAndGet());
        logger.info("querying: queued tasks = {}", selectPool.getQueue().size());
        for (int i = 0; i < 10_000; i++) {
            selectPool.submit(myService::queryMessage);
        }
        logger.info("query done");
    }

}
