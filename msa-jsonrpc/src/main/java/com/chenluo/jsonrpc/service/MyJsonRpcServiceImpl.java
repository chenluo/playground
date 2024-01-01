package com.chenluo.jsonrpc.service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class MyJsonRpcServiceImpl implements MyJsonRpcService {
    @Override
    public String service(String p1) {
        return "hello world, %s".formatted(p1);
    }
}
