package com.gateway.security.interceptor;

import cn.hutool.json.JSONUtil;
import com.gateway.security.constants.CloudConstant;
import com.gateway.security.properties.CloudSecurityProperties;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServerProtectInterceptor implements HandlerInterceptor {

    private CloudSecurityProperties properties;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {

        if (!properties.getOnlyFetchByGateway()) {
            return true;
        }

        String token = request.getHeader(CloudConstant.GATEWAY_TOKEN_HEADER);

        String gatewayToken = new String(Base64Utils.encode(CloudConstant.GATEWAY_TOKEN_VALUE.getBytes()));

        if (StringUtils.equals(gatewayToken, token)) {
            return true;
        } else {
            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("success", false);
            resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
            resultMap.put("message", "请通过网关访问资源");
            resultMap.put("timestamp", new Date().getTime());

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print(JSONUtil.parse(resultMap));
            response.flushBuffer();
            return false;
        }
    }

    public void setProperties(CloudSecurityProperties properties) {
        this.properties = properties;
    }
}
