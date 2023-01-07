package com.chenluo.servicefoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {
    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @GetMapping("greeting")
    public String greeting() {
        return String.format("hello from %s", webServerAppCtxt.getWebServer().getPort());
    }
}
