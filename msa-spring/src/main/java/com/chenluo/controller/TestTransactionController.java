package com.chenluo.controller;

import com.chenluo.model.mapper.SimpleMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestTransactionController {
    private final SimpleMapper simpleMapper;
    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public TestTransactionController(SimpleMapper simpleMapper) {
        this.simpleMapper = simpleMapper;
    }

    @GetMapping("tx")
    @Transactional
    public String tx(@RequestParam int id) {
        threadLocal.set("tx");
        innerTx();
        return "tx";
    }

    @Transactional
    public String innerTx() {
        return "innerTx";
    }
}
