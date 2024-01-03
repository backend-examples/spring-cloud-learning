package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayNacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayNacosConfigApplication.class, args);
    }
}
