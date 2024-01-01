package com.chenluo.service;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MyServiceTest {

    @Test
    void findById() {}

    @Test
    void removeById() {}

    @Test
    void findAndRemove() {}

    @Test
    void testReactor() {
        Mono<String> stringMono = Mono.just("S");
        StepVerifier.create(stringMono).expectNext("S").expectComplete().verify();
    }
}
