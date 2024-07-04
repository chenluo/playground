package com.chenluo.servicefoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceFooApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFooApplication.class, args);
    }
}
