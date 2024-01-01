package com.chenluo.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonRpcConfig {
    @Bean
    public AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }
}
