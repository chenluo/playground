package com.chenluo.jsonrpc.client;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;

@Component
public class MyJsonRpcClient implements InitializingBean {
    private final DeclarativeClient declarativeClient;

    public MyJsonRpcClient(DeclarativeClient declarativeClient) {
        this.declarativeClient = declarativeClient;
    }

    public String invoke() throws Throwable {
        JsonRpcHttpClient client =
                new JsonRpcHttpClient(new URL("http://localhost:8080/jsonrpc/serv"));
        Map<String, String> param = Map.of("p1", "p1");
        String result = client.invoke("service", param, String.class);
        System.out.println(result);
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        declarativeClient.hello("ping");

    }
}
