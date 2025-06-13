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

    @Override
    public void serviceObject(String p1, String p2) {
        System.out.println(p1+p2);
    }
}
