package com.gateway.security.config;

import com.gateway.security.properties.CloudSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(CloudSecurityProperties.class)
public class CloudSecurityAutoConfigure{

    @Bean
    public CloudSecurityInterceptorConfigure cloudSecurityInterceptorConfigure() {
        return new CloudSecurityInterceptorConfigure();
    }

}