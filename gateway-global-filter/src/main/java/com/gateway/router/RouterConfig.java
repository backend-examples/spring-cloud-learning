package com.gateway.router;

//@Configuration
//public class RouterConfig {
//
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder
//                .routes()
//                .route(
//                        "path_route_lb",
//                        // 第二个参数是个lambda实现，
//                        // 设置了配套条件是按照请求路径匹配，以及转发地址，
//                        // 注意lb://表示这是个服务名，要从
//                        r -> r.path("/**").uri("lb://nacos-provider")
//                )
//                .build();
//    }
//}
