package com.chenluo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZKTest {
    private final ExecutorService executor =
            new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10000),
                    new ThreadPoolExecutor.CallerRunsPolicy());
    private final Logger logger = LoggerFactory.getLogger(ZKTest.class);
    @LocalServerPort
    private int port;
    private URL base;
    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        base = new URL("http://localhost:" + port + "/");
    }

    public void testCreate(String url) {
        int count = 5;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            try {
                executor.submit(() -> {
                    ResponseEntity<String> response =
                            template.getForEntity(base.toString() + url, String.class);
                    if (!response.getStatusCode().equals(HttpStatus.OK)) {
                        logger.error("failed", response.getStatusCode());
                    }
                    latch.countDown();
                });
            } catch (RejectedExecutionException e) {
                logger.info("failed to execute: {}", i);
            }
        }
        logger.info("latch count: {}", latch.getCount());
        try {
            latch.await(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResponseEntity<String> response =
                template.getForEntity(base.toString() + "main/getZK?path=/newNode", String.class);
        Assertions.assertThat(response.getBody()).isEqualTo(String.valueOf(count));
    }

    //
    //    @Test
    //    public void testCreateUnsafe() {
    //        testCreate("main/testZKUnsafe");
    //    }

    //    @Test
    public void testCreate() {
        testCreate("main/testZK");
    }
}
