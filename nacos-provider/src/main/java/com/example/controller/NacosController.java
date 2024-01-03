package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/nacos")
public class NacosController {

    @GetMapping(value = "/test")
    public String test(){
        return "成功访问服务者接口111";
    }

    @GetMapping("/testHeader")
    public String testHeader(@RequestHeader HttpHeaders headers) {
        log.info("header: {}", headers);

        return "Hello World" + new Date();
    }
}

