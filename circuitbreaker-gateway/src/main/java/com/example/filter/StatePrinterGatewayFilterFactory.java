package com.example.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class StatePrinterGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Autowired
    ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory;

    @Override
    public String name() {
        return "CircuitBreakerStatePrinter";
    }

    @Override
    public GatewayFilter apply(Object config) {
        return new StatePrinterGatewayFilter(reactiveResilience4JCircuitBreakerFactory);
    }
}
