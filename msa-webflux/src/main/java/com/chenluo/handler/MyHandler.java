package com.chenluo.handler;

import com.chenluo.entity.SimpleEntity;
import com.chenluo.service.MyService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MyHandler {
    private final MyService myService;

    public MyHandler(MyService myService) {
        this.myService = myService;
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("\"msg\":\"Hello world!\""));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(
                        myService.findById(Integer.valueOf(request.pathVariable("id"))),
                        SimpleEntity.class));
    }

    public Mono<ServerResponse> findAndRemove(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(
                        myService.findAndRemove(Integer.valueOf(request.pathVariable("id"))),
                        SimpleEntity.class));
    }
}
