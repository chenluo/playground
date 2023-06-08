package com.chenluo.controller;

//import com.chenluo.base.spec.BaseService;

import com.chenluo.service.CacheableServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final CacheableServiceImpl cacheableService;


    public MainController(CacheableServiceImpl cacheableService) {
        this.cacheableService = cacheableService;
    }

    @GetMapping("get")
    public String get() {
        return cacheableService.get("");
    }
}
