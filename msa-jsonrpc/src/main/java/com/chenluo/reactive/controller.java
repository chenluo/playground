package com.chenluo.reactive;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class controller {
    @PostMapping("/test")
    Mono<String> test() {
        return Mono.just("hello");
    }
}
