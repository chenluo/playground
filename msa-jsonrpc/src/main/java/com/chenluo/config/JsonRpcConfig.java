package com.chenluo.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class JsonRpcConfig {
    @Bean
    public AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }

    @Bean
    public AutoJsonRpcClientProxyCreator autoJsonRpcClientProxyCreator()
            throws MalformedURLException {
        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
        creator.setBaseUrl(new URL("http://localhost:8080"));
        creator.setScanPackage("com.chenluo.jsonrpc.client");
        creator.setContentType("content-type: application/json-rpc");
        return creator;
    }
}
