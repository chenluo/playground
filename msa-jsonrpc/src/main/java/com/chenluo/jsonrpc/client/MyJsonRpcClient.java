package com.chenluo.jsonrpc.client;

import com.chenluo.jsonrpc.service.MyJsonRpcService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MyJsonRpcClient implements InitializingBean {
    private final MyJsonRpcServiceClient myJsonRpcServiceClient;

    public MyJsonRpcClient(MyJsonRpcServiceClient myJsonRpcServiceClient) {
        this.myJsonRpcServiceClient = myJsonRpcServiceClient;
    }


    public String invoke() {
//        String result = myJsonRpcServiceClient.serviceObject(new MyPayload());
        String result = myJsonRpcServiceClient.serviceObject("p1", "p2");
        System.out.println(result);
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
