package com.chenluo;

import com.chenluo.client.DeclarativeClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
@EnableScheduling
@EnableWebFlux
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(Main.class);
    }

    @Bean
    DeclarativeClient declarativeClient() {
        WebClientAdapter webClientAdapter = WebClientAdapter.create(WebClient.create());
        return HttpServiceProxyFactory.builderFor(webClientAdapter)
                .build()
                .createClient(DeclarativeClient.class);
    }
}
