package com.chenluo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MyHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        Mono.just("try doOnNext")
                .doOnNext(it -> System.out.println(it))
                .subscribe();
        return Mono.just("received")
                .flatMap(it -> {
                    System.out.println(it);
                    return Mono.just(it+" 1st flatMaped; ");
                }).flatMap(it ->
                        {
                            System.out.println(it);
                            return Mono.just(it +  " 2nd flatMapped;");
                        }
                )
                .flatMap(it -> {
                    System.out.println(it);
                    return Mono.just(it);
                })
                .flatMap(it -> {
                            return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue("\"msg\":\"Hello world!\""));
                        }
                );

    }
}
