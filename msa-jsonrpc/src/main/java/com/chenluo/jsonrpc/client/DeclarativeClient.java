package com.chenluo.jsonrpc.client;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("http://localhost:8080/")
public interface DeclarativeClient {
    String hello(@JsonRpcParam("arg1") String arg1);
}
