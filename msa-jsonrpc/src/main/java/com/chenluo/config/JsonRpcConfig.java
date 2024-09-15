package com.chenluo.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class JsonRpcConfig {
//    @Bean
    public AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }

    @Bean
    public AutoJsonRpcClientProxyCreator autoJsonRpcClientProxyCreator()
            throws MalformedURLException {
        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
        creator.setScanPackage("com.chenluo.jsonrpc");
        return creator;
    }
}
