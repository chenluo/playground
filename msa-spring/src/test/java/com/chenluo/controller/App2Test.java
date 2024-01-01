package com.chenluo.controller;

import com.chenluo.TestBase;
import com.chenluo.service.StatefulService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class App2Test extends TestBase {

    private final StatefulService service;

    @Autowired
    public App2Test(StatefulService service) {
        this.service = service;
    }

    @Test
    public void test() {
        service.increase();
    }

    @Test
    public void get() {
        System.out.println(service.get());
    }
}
