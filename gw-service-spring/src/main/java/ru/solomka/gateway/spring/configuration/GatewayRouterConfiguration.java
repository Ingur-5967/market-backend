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
                .route("product-service", relocate ->
                        relocate.path("/product/**")
                        .uri("http://product-service:8082")
                )
                .route("order-service", relocate ->
                        relocate.path("/order/**")
                        .uri("http://order-service:8083")
                )
                .build();
    }
}