package com.yogesh.employeeservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/employees")
@RefreshScope
public class SpringCloudBusTestingController {

    @Value("${spring.boot.message}")
    private String message;

    @GetMapping("/message")
    public String sendMessage(){
        return message;
    }
}
