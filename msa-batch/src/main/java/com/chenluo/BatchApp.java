package com.chenluo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BatchApp {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchApp.class, args)));
    }
}
