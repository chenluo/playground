package com.chenluo.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutionException;

class MyServiceTest {

    @Test
    void findById() {
    }

    @Test
    void removeById() {
    }

    @Test
    void findAndRemove() {
    }

    @Test
    void testReactor() {
        Mono<String> stringMono = Mono.just("S");
        StepVerifier.create(stringMono).expectNext("S").expectComplete().verify();
    }

    @Test
    void testWebclient() throws ExecutionException, InterruptedException {
        WebClient webClient = WebClient.create();
        //        Mono<String> stringMono = webClient.get().uri("https://www.google.com").retrieve()
        //                .bodyToMono(new ParameterizedTypeReference<String>() {
        //                }).doOnNext(s -> System.out.println(s));
        //
        //        String block = stringMono.block();
        //        System.out.println(block);


        ReactiveSecurityContextHolder.getContext().publishOn(Schedulers.boundedElastic()).map(ctx -> {
            String block = webClient.post().uri("https://www.google.com")
                    .retrieve()
                    .bodyToMono(String.class).block();
            System.out.println(block);
            return block;
        }).block();

    }
}
