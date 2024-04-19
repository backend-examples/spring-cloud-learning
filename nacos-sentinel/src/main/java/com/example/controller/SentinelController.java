package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sentinel")
public class SentinelController {

    @GetMapping("/message1")
    public String message1() {
        return "message1";
    }

    /**
     * 下单接口
     */
    @GetMapping("/order")
    public String order()  {
        return "下单成功..........";
    }

    /**
     * 支付接口
     */
    @GetMapping("/pay")
    public String pay()  {
        return "支付成功..........";
    }
}

