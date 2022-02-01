/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component(value = "myServiceA")
public class MyServiceAImpl implements MyService, InitializingBean, ApplicationContextAware {
    final Logger logger = LoggerFactory.getLogger(MyServiceAImpl.class);
    private ApplicationContext applicationContext;

    @Override
    public void testService1() {
        logger.warn("testService1 called");
        MyComplexServiceImpl bean = applicationContext.getBean(MyComplexServiceImpl.class);
        bean.call();
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
