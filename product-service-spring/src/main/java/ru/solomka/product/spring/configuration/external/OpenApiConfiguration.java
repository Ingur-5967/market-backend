package ru.solomka.product.spring.configuration.external;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product service",
                description = "Service for managing products and comments",
                version = "v1"
        ),
        servers = {
                @Server(
                        description = "Service documentation",
                        url = "http://localhost:8082"
                ),
        }
)
public class OpenApiConfiguration {
}