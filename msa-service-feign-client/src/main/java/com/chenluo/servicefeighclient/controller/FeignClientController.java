package com.chenluo.servicefeighclient.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class FeignClientController {
    Random random = new Random();
    @Autowired private EurekaClient eurekaClient;
    @Autowired private FeignClient feignClient;
    @Autowired private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(FeignClientController.class);

    @GetMapping("greetingNonFeign")
    @Scheduled(cron = "*/1 * * * * *")
    public String greetingNonFeign() {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("foo", false);
        logger.info("{}", instanceInfo);
        String url = instanceInfo.getHomePageUrl();
        String endpointUrl = "/greeting";
        ResponseEntity<String> resp = restTemplate.getForEntity(url + endpointUrl, String.class);
        if (resp.getStatusCode().is2xxSuccessful()) {
            logger.info("[greetingNonFeign] {}", resp.getBody());
            return resp.getBody();
        } else {
            logger.info("[greetingNonFeign] Failed with {}", resp.getStatusCode());
        }
        return "error";
    }

    @GetMapping("greeting")
    @Scheduled(cron = "*/1 * * * * *")
    public String greeting() {
        String greeting = feignClient.greeting();
        logger.info("[greeting] {}", greeting);
        return greeting;
    }
}
