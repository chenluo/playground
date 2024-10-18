package com.chenluo.jsonrpc.client;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/jsonrpc/serv")
public interface MyJsonRpcServiceClient {
    String service(@JsonRpcParam("p1") String p1);
}
