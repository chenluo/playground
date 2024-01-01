package com.chenluo.controller;

import com.chenluo.model.dto.SimpleEntity;
import com.chenluo.model.mapper.SimpleMapper;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
        SimpleEntity simpleEntity = simpleMapper.findById(id).get();
        return simpleEntity.toString();
    }
}
