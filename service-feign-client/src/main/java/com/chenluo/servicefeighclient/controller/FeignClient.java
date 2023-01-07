package com.chenluo.servicefeighclient.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.cloud.openfeign.FeignClient("foo")
public interface FeignClient {

    @GetMapping("greeting")
    public String greeting();
}
