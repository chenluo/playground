/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class MyMethodInterceptor implements MethodInterceptor {

    Logger logger = LoggerFactory.getLogger(MyMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.warn("[Before] MyMethodInterceptor invoke: {}.", invocation);
        Object result = invocation.proceed();
        logger.warn("[After] MyMethodInterceptor invoke: {}.", invocation);
        return result;
    }
}
