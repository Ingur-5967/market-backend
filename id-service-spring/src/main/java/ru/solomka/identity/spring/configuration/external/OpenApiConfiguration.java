package ru.solomka.identity.spring.configuration.external;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Identity service",
                description = "Service for authentication and validate users",
                version = "v1"
        ),
        servers = {
                @Server(
                        description = "Service documentation",
                        url = "http://localhost:8081"
                ),
        }
)
public class OpenApiConfiguration {
}