/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.controller;

import com.chenluo.base.spec.BaseService;
import com.chenluo.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/main/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final AtomicLong atomicLong = new AtomicLong(1);

    private final MyService myService;
    private final BaseService baseService;

    @Autowired
    public MainController(@Qualifier(value = "myServiceA") MyService myService,
                          BaseService baseService) {
        this.myService = myService;
        this.baseService = baseService;
    }


    private volatile long volatileLong = 1;

    private Long longCounter = 1L;

    @GetMapping("test")
    @ResponseBody
    public boolean test() {
        System.out.println(volatileLong);
        myService.testService1();
        volatileLong = 2;
        return true;
    }
}
