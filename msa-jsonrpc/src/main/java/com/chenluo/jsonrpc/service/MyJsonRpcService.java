package com.chenluo.jsonrpc.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("http://localhost:8080/jsonrpc/serv/")
public interface MyJsonRpcService {
    String service(@JsonRpcParam("p1") String p1);

    String call() throws Throwable;
}
