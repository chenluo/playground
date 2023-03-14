package com.chenluo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassicController {
    @GetMapping("get")
    public String get() {
        return "get";
    }
}
