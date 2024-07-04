package com.chenluo.servicediscover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaServer
@EnableScheduling
public class ServiceDiscoverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoverApplication.class, args);
    }
}
