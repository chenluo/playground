package com.chenluo.controller;

import com.chenluo.MysqlTestBase;
import com.chenluo.service.StatefulService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class App1Test extends MysqlTestBase {
    private final StatefulService service;

    @Autowired
    public App1Test(StatefulService service) {
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
