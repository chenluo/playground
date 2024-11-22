package com.chenluo.jsonrpc.client;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcParamsPassMode;
import com.googlecode.jsonrpc4j.JsonRpcService;
import org.springframework.cache.annotation.Cacheable;

@JsonRpcService("/jsonrpc/serv")
public interface MyJsonRpcServiceClient {
    String service(@JsonRpcParam("p1") String p1);
    @Cacheable(value = "cache2", key = "#p1")
    String serviceObject(String p1, String p2);
}
