/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.extension.spring;

import com.chenluo.proxy.MyMethodInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    final Logger logger = LoggerFactory.getLogger(MyBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if (beanName.equals("myService")) {
            logger.warn(
                    "postProcessBeforeInitialization called for {} with name: {}", bean, beanName);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (beanName.equals("myService")) {
            logger.warn(
                    "postProcessAfterInitialization called for {} with name: {}", bean, beanName);
            ProxyFactory factory = new ProxyFactory(bean);
            factory.addAdvice(new MyMethodInterceptor());
            Object proxy = factory.getProxy();
            return proxy;
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
