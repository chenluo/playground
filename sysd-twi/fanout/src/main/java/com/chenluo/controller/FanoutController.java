package com.chenluo.controller;

import com.chenluo.service.FanoutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FanoutController {
    private final FanoutService fanoutService;

    public FanoutController(FanoutService fanoutService) {
        this.fanoutService = fanoutService;
    }

    @PostMapping("")
    public String fanout(@RequestBody Map<String, String> req) {
        return fanoutService.fanout(req.get("uid"), req.get("tid"));
    }

    @PostMapping("/rebuild")
    public String rebuild(@RequestParam String uid) {
        return fanoutService.rebuild(uid);
    }
}
