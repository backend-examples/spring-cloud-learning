package com.gateway.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cloud.security")
public class CloudSecurityProperties {

    /**
     * 是否只能通过网关获取资源
     * 默认为True
     */
    private Boolean onlyFetchByGateway = Boolean.TRUE;

}
