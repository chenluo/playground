package com.chenluo;

import reactor.core.publisher.Mono;

public class MonoTest {
    public static void main(String[] args) {
        Mono.just("data")
                .doOnNext(it -> System.out.println(it+"doOnNext;"))
                .doOnSuccess(it -> System.out.println(it+"doOnSuccess"))
                .flatMap(it-> {
                    System.out.println(it);
                    return Mono.just(it+"flatmap;");
                }).subscribe(it -> System.out.println(it));
    }
}
