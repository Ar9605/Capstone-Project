package com.application.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://auth-service"))
                
                .route("user-service", r -> r.path("/api/v1/users/**")
                        .uri("lb://user-service"))
                
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .uri("lb://product-service"))
                
                .route("inventory-service", r -> r.path("/api/v1/inventory/**")
                        .uri("lb://inventory-service"))
                
                .route("management-service", r -> r.path("/api/v1/management/**")
                        .uri("lb://management-service"))

                // --- Swagger API Docs Routes (with RewritePath) ---
                .route("user-service-docs", r -> r.path("/v3/api-docs/user-service")
                        .filters(f -> f.rewritePath("/v3/api-docs/user-service", "/v3/api-docs"))
                        .uri("lb://user-service"))

                .route("product-service-docs", r -> r.path("/v3/api-docs/product-service")
                        .filters(f -> f.rewritePath("/v3/api-docs/product-service", "/v3/api-docs"))
                        .uri("lb://product-service"))

                .route("inventory-service-docs", r -> r.path("/v3/api-docs/inventory-service")
                        .filters(f -> f.rewritePath("/v3/api-docs/inventory-service", "/v3/api-docs"))
                        .uri("lb://inventory-service"))

                .route("management-service-docs", r -> r.path("/v3/api-docs/management-service")
                        .filters(f -> f.rewritePath("/v3/api-docs/management-service", "/v3/api-docs"))
                        .uri("lb://management-service"))
                
                .build();
    }
}
