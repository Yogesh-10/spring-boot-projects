package com.yogesh.departmentservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController("message")
@AllArgsConstructor
public class SpringCloudBusTestingController {

    @Value("${spring.boot.message}")
    private String message;

    @GetMapping
    public String sendMessage(){
        return message;
    }
}
