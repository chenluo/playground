package com.chenluo.controller;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutionException;

@RestController
public class ClassicController {
    @GetMapping("get")
    public Mono<String> get() {
        return ReactiveSecurityContextHolder.getContext()
                .publishOn(Schedulers.boundedElastic())
                .map(
                        ctx -> {
                            WebClient webClient = WebClient.create();
                            String block = null;
                            block =
                                    webClient
                                            .get()
                                            .uri("https://www.google.com")
                                            .retrieve()
                                            .bodyToMono(String.class)
                                            .block();
                            System.out.println(block);
                            return block;
                        });
    }

    @GetMapping("get2")
    public Mono<String> get2() {
        return ReactiveSecurityContextHolder.getContext()
                .map(
                        ctx -> {
                            WebClient webClient = WebClient.create();
                            String block = null;
                            try {
                                block =
                                        webClient
                                                .get()
                                                .uri("https://www.google.com")
                                                .retrieve()
                                                .bodyToMono(String.class)
                                                .map(String::toLowerCase)
                                                .toFuture()
                                                .get();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(block);
                            return block;
                        });
    }
}
