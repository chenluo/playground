package com.chenluo.controller;

// import com.chenluo.base.spec.BaseService;

import com.chenluo.data.dto.SmallTbl;
import com.chenluo.data.repo.SmallTblRepository;
import com.chenluo.kafka.MessageProducer;
import com.chenluo.service.CacheableServiceImpl;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// @RestController
@RequestMapping("/main/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final CacheableServiceImpl cacheableService;

    private final SmallTblRepository smallTblRepository;
    private final MessageProducer messageProducer;

    public MainController(CacheableServiceImpl cacheableService,
                          SmallTblRepository smallTblRepository, MessageProducer messageProducer) {
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
}
