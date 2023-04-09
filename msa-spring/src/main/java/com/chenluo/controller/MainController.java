package com.chenluo.controller;

//import com.chenluo.base.spec.BaseService;

import com.chenluo.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final MyService myService;

    @Autowired
    public MainController(@Qualifier(value = "myServiceA") MyService myService) {
        this.myService = myService;
    }

}
