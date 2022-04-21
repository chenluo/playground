package com.chenluo.autoconfigure;

import com.chenluo.base.impl.BaseServiceImpl;
import com.chenluo.base.spec.BaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAutoConfiguration {
    @Bean
    public BaseService baseService() {
        return new BaseServiceImpl();
    }

}
