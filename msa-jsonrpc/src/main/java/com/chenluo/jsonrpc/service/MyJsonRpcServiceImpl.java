package com.chenluo.jsonrpc.service;

import com.chenluo.jsonrpc.client.MyPayload;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class MyJsonRpcServiceImpl implements MyJsonRpcService {
    @Override
    public String service(String p1) {
        return "hello world, %s".formatted(p1);
    }

    @Override
    public String serviceObject(String p1, String p2) {
        return p1+p2;
    }
}
