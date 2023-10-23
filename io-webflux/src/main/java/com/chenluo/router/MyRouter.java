package com.chenluo.router;

import com.chenluo.handler.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MyRouter {
    @Bean
    public RouterFunction<ServerResponse> routeMyHandlerHello(MyHandler myHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        myHandler::hello)
                .andRoute(RequestPredicates.GET("/mysql/get/{id}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        myHandler::getById).andRoute(
                        RequestPredicates.GET("/mysql/findAndRemove/{id}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        myHandler::findAndRemove);
    }
}
