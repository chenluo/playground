package controller;

import com.chenluo.main.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class ControllerTest {
    @LocalServerPort
    private int port;

    private URL base;

    private final Logger logger = LoggerFactory.getLogger(ControllerTest.class);

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void testController() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(500), new ThreadFactory(){
            private AtomicInteger threadNo = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "thread-"+threadNo.incrementAndGet());
            }
        },
                new ThreadPoolExecutor.CallerRunsPolicy());
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 100000; i++) {
            try {
                executor.submit(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        String name = Thread.currentThread().getName();
                        if (name.equals("main")) {
                            longAdder.increment();
                        }
                        logger.warn("thread name={}", name);

                        ResponseEntity<Boolean> response = template.getForEntity(base.toString() + "main/test/",
                                Boolean.class);
                        Assertions.assertEquals(Boolean.TRUE, response.getBody());
                        return;
                    }
                });
            } catch (RejectedExecutionException e) {
                logger.error("task rejected", e);
                longAdder.increment();
            }
        }

        executor.shutdown();
        while (true) {
            try {
                if (executor.awaitTermination(10, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        logger.warn("{} tasks rejected", longAdder.longValue());
    }
}
