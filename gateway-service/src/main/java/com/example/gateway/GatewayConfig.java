package com.example.gateway;

import com.example.gateway.filter.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                .route("service-a", r -> r.path("/service-a/**")
                        .filters(f -> f.filter(authFilter).rewritePath("/service-a/(?<segment>.*)", "/${segment}"))
                        .uri("lb://service-a"))
                .route("service-b", r -> r.path("/service-b/**")
                        .filters(f -> f.filter(authFilter).rewritePath("/service-b/(?<segment>.*)", "/${segment}"))
                        .uri("lb://service-b"))
                .build();
    }
}
