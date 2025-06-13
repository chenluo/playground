package com.chenluo;

import reactor.core.publisher.Mono;

public class MonoTest {
    public static void main(String[] args) {
        try {
            foo().block();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    static Mono<String> foo() {
        return Mono.just(1).map(it -> it*2)
                .handle((it, sink) -> {
                    if(it > 0) {
                        sink.error(new RuntimeException("fail"));
                        return;
                    }
                    sink.next("success");
                });
    }
}
