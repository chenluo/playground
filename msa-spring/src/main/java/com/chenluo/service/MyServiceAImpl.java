/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.service;

import com.chenluo.jpa.dto.Message;
import com.chenluo.jpa.repo.MessageRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service(value = "myServiceA")
public class MyServiceAImpl implements MyService, InitializingBean, ApplicationContextAware {
    final Logger logger = LogManager.getLogger(MyServiceAImpl.class);
    private ApplicationContext applicationContext;

    private final MessageRepo messageRepo;

    public MyServiceAImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public void testService1() {
        logger.warn("testService1 called");
        MyComplexServiceImpl bean = applicationContext.getBean(MyComplexServiceImpl.class);
        bean.call();
    }

    @Override
    public void insertMessage() {
        Message message = messageRepo.save(
                new Message(null, UUID.randomUUID().toString(), "type1", 1, 1, 1, 1,
                        String.format("{\"detail\":\"%s\"}", UUID.randomUUID()),
                        LocalDateTime.now()));
        //        logger.info("{}", message);
    }

    @Override
    public void queryMessage() {
        Optional<Message> messageOpt =
                messageRepo.findById(ThreadLocalRandom.current().nextInt(10_000_000));
        messageOpt.get();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet of bean {}", MyServiceAImpl.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
