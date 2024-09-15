package com.chenluo.jsonrpc.service;

import com.chenluo.jsonrpc.client.MyJsonRpcClient;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class MyJsonRpcServiceImpl implements MyJsonRpcService {
    private final MyJsonRpcClient client;

    public MyJsonRpcServiceImpl(MyJsonRpcClient client) {
        this.client = client;
    }

    @Override
    public String service(String p1) {
        return "hello world, %s".formatted(p1);
    }

    @Override
    public String call() throws Throwable {
        return client.invoke();
    }
}
