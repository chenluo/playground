package com.chenluo.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface DeclarativeClient {
    @GetExchange("get")
    public String get();
}
