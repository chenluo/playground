package com.chenluo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class FanoutClient {

    @Value("${twi.fanout.url:http://localhost:8081}")
    private String fanoutUrl;
    private final RestTemplate restTemplate;


    public FanoutClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void callFanoutService(String uid, String tid) {
        ResponseEntity<String> res = restTemplate.postForEntity(fanoutUrl+"/fanout",
                Map.of("uid", uid, "tid", tid),
                String.class);
    }
}
