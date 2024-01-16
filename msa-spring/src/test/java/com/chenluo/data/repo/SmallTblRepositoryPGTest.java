package com.chenluo.data.repo;

import com.chenluo.PGTestBase;
import com.chenluo.data.dto.LargeTbl;
import com.chenluo.data.dto.LargeTblHashPartitioning;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmallTblRepositoryPGTest extends PGTestBase {
    @Autowired private LargeTblRepository repository;
    @Autowired LargeTblHashPartitioningRepository largeTblHashPartitioningRepository;
    private final int amount = 1_000;
    StopWatch stopWatch = new StopWatch();

    @BeforeEach
    public void before() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    @AfterEach
    public void after() {
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
        System.out.println(amount / stopWatch.getTotalTime(TimeUnit.SECONDS) + " qps");
    }

    @Test
    @Order(1)
    public void insert() {
        for (int i = 0; i < amount; i++) {
            repository.save(new LargeTbl());
        }
    }

    @Test
    @Order(2)
    public void insertPartitioning() {
        for (int i = 0; i < amount; i++) {
            largeTblHashPartitioningRepository.save(new LargeTblHashPartitioning());
        }
    }

    @Test
    @Order(3)
    public void batchInsert100() {
        List<LargeTbl> batch = new ArrayList<>();
        for (int i = 0; i < amount / 100; i++) {
            for (int j = 0; j < 100; j++) {
                batch.add(new LargeTbl());
            }
            repository.saveAll(batch);
            batch.clear();
        }
    }

    @Test
    @Order(4)
    public void batchInsert100Partitioning() {
        List<LargeTblHashPartitioning> batch = new ArrayList<>();
        for (int i = 0; i < amount / 100; i++) {
            for (int j = 0; j < 100; j++) {
                batch.add(new LargeTblHashPartitioning());
            }
            largeTblHashPartitioningRepository.saveAll(batch);
            batch.clear();
        }
    }

    @Test
    @Order(5)
    public void batchInsert500() throws Exception {
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            futures.add(
                    CompletableFuture.runAsync(
                            () -> {
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
    }

    @Test
    @Order(6)
    public void batchInsert500Partitioning() throws Exception {
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            futures.add(
                    CompletableFuture.runAsync(
                            () -> {
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
    }

    @Test
    @Order(1000)
    public void query() {
        long count = repository.count();
        Random r = new Random(1L);
        for (int i = 0; i < amount; i++) {
            Optional<LargeTbl> byId = repository.findById(r.nextLong(count) + 1);
            //            byId.ifPresent(System.out::println);
        }
    }

    @Test
    @Order(1001)
    public void queryPartitioning() {
        long count = largeTblHashPartitioningRepository.count();
        Random r = new Random(1L);
        for (int i = 0; i < amount; i++) {
            Optional<LargeTblHashPartitioning> byId =
                    largeTblHashPartitioningRepository.findById(r.nextLong(count) + 1);
            //            byId.ifPresent(System.out::println);
        }
    }
}
