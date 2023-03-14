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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component(value = "myServiceB")
public class MyServiceBImpl implements MyService, InitializingBean {
    final Logger logger = LoggerFactory.getLogger(MyServiceBImpl.class);

    @Override
    public void testService1() {
        logger.warn("testService1 called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet of bean {}", MyServiceBImpl.class);
    }
}
