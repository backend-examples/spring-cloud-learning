package com.example.config;

import com.example.function.RequestBodyRewrite;
import com.example.function.ResponseBodyRewrite;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, ObjectMapper objectMapper) {
        return builder
                .routes()
                .route("path_route_change",
                        r -> r.path("/nacos/change")
                                .filters(f -> f
                                        .modifyRequestBody(String.class,String.class,new RequestBodyRewrite(objectMapper))
                                        .modifyResponseBody(String.class, String.class, new ResponseBodyRewrite(objectMapper))
                                )
                                .uri("http://localhost:9001"))
                .build();
    }
}

