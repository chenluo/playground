package com.chenluo.jsonrpc.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/jsonrpc/serv")
public interface MyJsonRpcService {
    String service(@JsonRpcParam("p1") String p1);

    void serviceObject(@JsonRpcParam("p1") String p1, @JsonRpcParam("p2") String p2);
}
