package com.chenluo.data.repo;

import com.chenluo.data.dto.LargeTbl;
import com.chenluo.data.dto.LargeTblHashPartitioning;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@SpringBootTest
class SmallTblRepositoryTest {
    @Autowired
    private LargeTblRepository repository;
    @Autowired
    LargeTblHashPartitioningRepository largeTblHashPartitioningRepository;
    private final int amount = 1_000_000;

    @Test
    public void insert() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < amount; i++) {
            repository.save(new LargeTbl());
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount * 1.0 / stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }

    @Test
    public void insertPartitioning() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < amount; i++) {
            largeTblHashPartitioningRepository.save(new LargeTblHashPartitioning());
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount * 1.0 / stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }

    @Test
    public void batchInsert100() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<LargeTbl> batch = new ArrayList<>();
        for (int i = 0; i < amount / 100; i++) {
            for (int j = 0; j < 100; j++) {
                batch.add(new LargeTbl());
            }
            repository.saveAll(batch);
            batch.clear();
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount * 1.0 / stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }

    @Test
    public void batchInsert100Partitioning() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<LargeTblHashPartitioning> batch = new ArrayList<>();
        for (int i = 0; i < amount / 100; i++) {
            for (int j = 0; j < 100; j++) {
                batch.add(new LargeTblHashPartitioning());
            }
            largeTblHashPartitioningRepository.saveAll(batch);
            batch.clear();
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount * 1.0 / stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }

    @Test
    public void batchInsert500() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(CompletableFuture.runAsync(() -> {
                List<LargeTbl> batch = new ArrayList<>();
                for (int j = 0; j < amount / 500; j++) {
                    for (int k = 0; k < 500; k++) {
                        batch.add(new LargeTbl());
                    }
                    repository.saveAll(batch);
                    batch.clear();
                }
            }));
        }
        for (Future future : futures) {
            future.get();
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount * 10 * 1.0 / stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }

    @Test
    public void batchInsert500Partitioning() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(CompletableFuture.runAsync(() -> {
                List<LargeTblHashPartitioning> batch = new ArrayList<>();
                for (int j = 0; j < amount / 500; j++) {
                    for (int k = 0; k < 500; k++) {
                        batch.add(new LargeTblHashPartitioning());
                    }
                    largeTblHashPartitioningRepository.saveAll(batch);
                    batch.clear();
                }
            }));
        }
        for (Future future : futures) {
            future.get();
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount * 10 * 1.0 / stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }

    @Test
    public void query() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long count = repository.count();
        Random r = new Random(1L);
        for (int i = 0; i < amount/10; i++) {
            Optional<LargeTbl> byId = repository.findById(r.nextLong(count) + 1);
//            byId.ifPresent(System.out::println);
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount/10/ stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }

    @Test
    public void queryPartitioning() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long count = largeTblHashPartitioningRepository.count();
        Random r = new Random(1L);
        for (int i = 0; i < amount/10; i++) {
            Optional<LargeTblHashPartitioning> byId =
                    largeTblHashPartitioningRepository.findById(r.nextLong(count) + 1);
//            byId.ifPresent(System.out::println);
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount/10/ stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }
}
