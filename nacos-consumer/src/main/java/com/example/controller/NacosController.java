package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
public class NacosController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${service-url.nacos-provider}")
    private String serviceUrl;

    @GetMapping("/getMessage")
    public String test(){
        //使用 LoadBalanceClient 和 RestTemplate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceUrl);
        //调用 nacos-provider 的接口
        String url = String.format("http://%s:%s/nacos/test",serviceInstance.getHost(),serviceInstance.getPort());
        return restTemplate.getForObject(url,String.class);
    }
}

