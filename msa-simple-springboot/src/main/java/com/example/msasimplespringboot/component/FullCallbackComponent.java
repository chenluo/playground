package com.example.msasimplespringboot.component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import static com.example.msasimplespringboot.LogUtil.logStackTrace;

@Component
public class FullCallbackComponent implements BeanPostProcessor, //
                                              BeanNameAware, //
                                              InitializingBean, //
                                              DisposableBean //
{
    private final Logger logger = LoggerFactory.getLogger(FullCallbackComponent.class);

    @PostConstruct
    public void postConstruct() {
        logStackTrace();
    }

    @PreDestroy
    public void preDestroy() {
        logStackTrace();
    }

    @Override
    public void setBeanName(String name) {
        logStackTrace();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        logStackTrace();
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        logStackTrace();
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public void destroy() throws Exception {
        logStackTrace();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logStackTrace();
    }
}
