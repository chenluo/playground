package com.chenluo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public abstract class User {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    RestTemplate client = new RestTemplate();
    protected final String uid;
    private int visit = 0;

    public User(String uid) {
        this.uid = uid;
    }

    public abstract void action();
    public void start() {
        Instant start = Instant.now();
        while (!Thread.interrupted()) {
            try {
                action();
                visit++;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Instant end = Instant.now();
        logger.info("{} per second", new BigDecimal(visit).divide(new BigDecimal(end.getEpochSecond()-start.getEpochSecond()), RoundingMode.CEILING));
    }

    public void postQueue() {
        client.postForEntity("http://localhost:8080/write/post/queue",
                Map.of("uid", uid,
                        "content", UUID.randomUUID().toString()),
                String.class);
    }

    public void post() {
        client.postForEntity("http://localhost:8080/write/post",
                Map.of("uid", uid,
                        "content", UUID.randomUUID().toString()),
                String.class);
    }

    public void home() {
        ResponseEntity<String> homePage= client.getForEntity("http://localhost:8080/read/home?uid={uid}&start={start}", String.class,
                Map.of("uid", uid, "start", 0));
    }
    public void homeCache() {
        ResponseEntity<String> homePage= client.getForEntity("http://localhost:8080/read/home/cache?uid={uid}&start={start}", String.class,
                Map.of("uid", uid, "start", 0));
    }

}
