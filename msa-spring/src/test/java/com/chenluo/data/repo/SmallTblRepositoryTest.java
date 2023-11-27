package com.chenluo.data.repo;

import com.chenluo.data.dto.LargeTbl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@SpringBootTest
@TestPropertySources({@TestPropertySource(properties = {"spring.datasource" +
        ".url=jdbc:mysql://localhost:3306/test_db", "spring.datasource.username=root", "spring" +
        ".datasource.password=mysql", "spring.datasource.hikari.maximum-pool-size=10"})})
class SmallTblRepositoryTest {
    @Autowired
    private LargeTblRepository repository;
    private int amount = 1_000_00;

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
    public void batchInsert500() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
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
        System.out.println(amount * 100 * 1.0 / stopWatch.getTotalTimeMillis() * 1000 + " qps");
    }


}