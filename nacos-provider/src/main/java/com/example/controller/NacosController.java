package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/nacos")
public class NacosController {

    private String dateStr(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    @GetMapping(value = "/test")
    public String test(){
        return "成功访问服务者接口111";
    }

    @GetMapping("/testHeader")
    public String testHeader(@RequestHeader HttpHeaders headers) {
        log.info("header: {}", headers);

        return "Hello World" + new Date();
    }

    @GetMapping(value = "/account/{id}")
    public String account(@PathVariable("id") int id) throws InterruptedException {
        if(1==id) {
            Thread.sleep(500);
        }

        return "Account" + dateStr();
    }

    @GetMapping("/userinfo")
    public String userInfo(@RequestParam("username") String username) {
        return  "hello " + username + ", " + dateStr();
    }

    @PostMapping("/change")
    public Map<String, Object> change(@RequestBody Map<String, Object> map) {
        map.put("response-tag", dateStr());
        return map;
    }
}

