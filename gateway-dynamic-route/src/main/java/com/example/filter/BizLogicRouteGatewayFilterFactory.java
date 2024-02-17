package com.example.filter;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Component
@Slf4j
public class BizLogicRouteGatewayFilterFactory extends AbstractGatewayFilterFactory<BizLogicRouteGatewayFilterFactory.BizLogicRouteConfig> {

    private static final String TAG_TEST_USER = "tag-test-user";

    public BizLogicRouteGatewayFilterFactory() {
        super(BizLogicRouteConfig.class);
    }

    @Override
    public GatewayFilter apply(BizLogicRouteConfig config) {

        return (exchange, chain) -> {
            // 本次的请求对象
            ServerHttpRequest request =  exchange.getRequest();

            // 调用方请求时的path
            String rawPath = request.getURI().getRawPath();

            log.info("rawPath [{}]", rawPath);

            // 请求头
            HttpHeaders headers = request.getHeaders();

            // 请求方法
            HttpMethod httpMethod = request.getMethod();

            // 请求参数
            MultiValueMap<String, String> queryParams = request.getQueryParams();

            // 这就是定制的业务逻辑，isTestUser等于ture代表当前请求来自测试用户，需要被转发到测试环境
            boolean isTestUser = false;

            // 如果header中有tag-test-user这个key，并且值等于true(不区分大小写)，
            // 就认为当前请求是测试用户发来的
            if (headers.containsKey(TAG_TEST_USER)) {
                String tageTestUser = headers.get(TAG_TEST_USER).get(0);

                if ("true".equalsIgnoreCase(tageTestUser)) {
                    isTestUser = true;
                }
            }

            URI uri;

            if (isTestUser) {
                log.info("这是测试用户的请求");
                // 从配置文件中得到测试环境的uri
                uri = UriComponentsBuilder.fromHttpUrl(config.getTestEnvUri() + rawPath).queryParams(queryParams).build().toUri();
            } else {
                log.info("这是普通用户的请求");
                // 从配置文件中得到正式环境的uri
                uri = UriComponentsBuilder.fromHttpUrl(config.getProdEnvUri() + rawPath).queryParams(queryParams).build().toUri();
            }

            // 生成新的Request对象，该对象放弃了常规路由配置中的spring.cloud.gateway.routes.uri字段
            ServerHttpRequest serverHttpRequest = request.mutate().uri(uri).method(httpMethod).headers(httpHeaders -> httpHeaders = httpHeaders).build();

            // 取出当前的route对象
            Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
            //从新设置Route地址
            Route newRoute =
                    Route.async().asyncPredicate(route.getPredicate()).filters(route.getFilters()).id(route.getId())
                            .order(route.getOrder()).uri(uri).build();
            // 放回exchange中
            exchange.getAttributes().put(GATEWAY_ROUTE_ATTR,newRoute);

            // 链式处理，交给下一个过滤器
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        };
    }

    /**
     * 这是过滤器的配置类，配置信息会保存在此处
     */
    @Data
    @ToString
    public static class BizLogicRouteConfig {
        // 生产环境的服务地址
        private String prodEnvUri;

        // 测试环境的服务地址
        private String testEnvUri;
    }
}
