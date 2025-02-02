package com.chenluo.controller;

import com.chenluo.client.FanoutClient;
import com.chenluo.service.DbWriter;
import com.chenluo.service.QueueService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Writer {
    private final DbWriter dbWriter;
    private final QueueService queueService;
    private final FanoutClient fanoutClient;

    public Writer(DbWriter dbWriter, QueueService queueService, FanoutClient fanoutClient) {
        this.dbWriter = dbWriter;
        this.queueService = queueService;
        this.fanoutClient = fanoutClient;
    }

    @PostMapping("post")
    public String post(@RequestBody PostRequest request) {
        String tid = dbWriter.save(request.uid, request.content);
        fanoutClient.callFanoutService(request.uid, tid);
        return "success";
    }

    @PostMapping("post/queue")
    public String postQueue(@RequestBody PostRequest request) {
        String tid = dbWriter.save(request.uid, request.content);
        queueService.sendMsg(request.uid, tid);
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
