package com.chenluo.controller;

import com.chenluo.client.FanoutClient;
import com.chenluo.service.DbWriter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Writer {
    private final DbWriter dbWriter;
    private final FanoutClient fanoutClient;

    public Writer(DbWriter dbWriter, FanoutClient fanoutClient) {
        this.dbWriter = dbWriter;
        this.fanoutClient = fanoutClient;
    }

    @PostMapping("post")
    public String post(@RequestBody PostRequest request) {
        String tid = dbWriter.save(request.uid, request.content);
        fanoutClient.callFanoutService(request.uid, tid);
        return "success";
    }

    static class PostRequest {
        String uid;
        String content;

        public PostRequest(String uid, String content) {
            this.uid = uid;
            this.content = content;
        }
    }
}
