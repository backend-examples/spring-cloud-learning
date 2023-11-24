package com.example.controller;

import com.example.enity.ConfigUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class NacosConfigController {

    @Autowired
    private ConfigUser configUser;

    @GetMapping("/test")
    public String test() {
        return "current user age is:" + configUser.getAge();
    }
}

