package com.chenluo.service;

import com.chenluo.entity.Twi;
import com.chenluo.repo.TwiRepo;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class DbWriter {
    private final TwiRepo twiRepo;

    public DbWriter(TwiRepo twiRepo) {
        this.twiRepo = twiRepo;
    }

    public String save(String uid, String content) {
        Twi saved = twiRepo.save(new Twi(generateTid(), uid, content, ZonedDateTime.now()));
        return saved.tid;
    }

    private String generateTid() {
        return UUID.randomUUID().toString();
    }
}
