package com.chenluo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    @PostMapping("upload")
    public String upload(@RequestBody MultipartFile file) throws IOException {
        System.out.println(file);
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        System.out.println(new String(file.getBytes()));
        return "success";
    }

}
