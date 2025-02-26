package ru.solomka.gateway.spring.configuration;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouterConfiguration {

    @Bean
    public RouteLocator redirectRequestRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("identity-service", relocate ->
                        relocate.path("/identity/**")
                        .uri("http://identity-service:8081")
                )
                .route("profile-service", relocate ->
                        relocate.path("/profile/**")
                        .uri("http://localhost:8082")
                )
                .route("product-service", relocate ->
                        relocate.path("/product/**")
                        .uri("lb://PRODUCT-SERVICE")
                )
                .build();
    }
}