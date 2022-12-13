
package com.chenluo.controller;

//import com.chenluo.base.spec.BaseService;
import com.chenluo.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/main/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final AtomicLong atomicLong = new AtomicLong(1);

    private final MyService myService;
//    private final BaseService baseService;

    @Autowired
    public MainController(@Qualifier(value = "myServiceA") MyService myService
//            ,
//                          BaseService baseService
    ) {
        this.myService = myService;
//        this.baseService = baseService;
    }


    private volatile long volatileLong = 1;

    private Long longCounter = 1L;

    @GetMapping("test")
    @ResponseBody
    public boolean test() {
        //        System.out.println(baseService.init());
        System.out.println(volatileLong);
        myService.testService1();
        volatileLong = 2;
        int[] ints = new int[1000000];
        return true;
    }

    @GetMapping("sleep")
    public String sleep() throws InterruptedException {
        ZonedDateTime now = ZonedDateTime.now();
        while (ZonedDateTime.now().isBefore(now.plusSeconds(1L))) {

        }
        return "sleep";
    }

    @GetMapping("path1")
    public String path1() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        while (LocalDateTime.now().isBefore(now.plusNanos(10000000*40))) {

        }
        return "path1";
    }

    @GetMapping("path2")
    public String path2() throws InterruptedException {

        return "path2";
    }
}
