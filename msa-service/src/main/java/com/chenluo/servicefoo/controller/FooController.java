package com.chenluo.servicefoo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {
    @Value("${server.port}")
    private int port;

    @GetMapping("greeting")
    public String greeting() {
        return String.format("hello from %s", port);
    }
}
