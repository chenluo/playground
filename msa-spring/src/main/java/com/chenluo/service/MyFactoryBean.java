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
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyFactoryBean implements FactoryBean<MyComplexServiceImpl> {
    private final Logger logger = LoggerFactory.getLogger(MyFactoryBean.class);

    @Override
    public MyComplexServiceImpl getObject() throws Exception {
        logger.warn("factoryBean getObject called.");
        return new MyComplexServiceImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return MyComplexServiceImpl.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
