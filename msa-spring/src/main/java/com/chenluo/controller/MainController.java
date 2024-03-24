package com.chenluo.controller;

import com.chenluo.data.dto.SmallTbl;
import com.chenluo.data.repo.SmallTblRepository;
import com.chenluo.kafka.MessageProducer;
import com.chenluo.service.CacheableServiceImpl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/main/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final CacheableServiceImpl cacheableService;

    private final SmallTblRepository smallTblRepository;
    private final MessageProducer messageProducer;

    public MainController(
            CacheableServiceImpl cacheableService,
            SmallTblRepository smallTblRepository,
            MessageProducer messageProducer) {
        this.cacheableService = cacheableService;
        this.smallTblRepository = smallTblRepository;
        this.messageProducer = messageProducer;
    }

    @GetMapping("get")
    public String get() {
        return cacheableService.get("");
    }

    @PostMapping("post")
    @Transactional
    public boolean post() {

        smallTblRepository.save(new SmallTbl());

        messageProducer.getProducer().send(new ProducerRecord<>("topic", "msg"));
        return true;
    }

    @GetMapping("getWithException")
    public ResponseEntity<String> getWithException() {
        return ResponseEntity.internalServerError().body("xxx exception");
    }

    @GetMapping("getWithCustomizedException")
    public String getWithCustomizedException() {
        throw new CustomizedException("exception 1");
    }

    @GetMapping("testCacheInLoop")
    public Set<String> testCacheInLoop(){
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(cacheableService.get("key"));
        }
        return set;
    }
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private static class CustomizedException extends RuntimeException {
        private String message;

        private CustomizedException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
