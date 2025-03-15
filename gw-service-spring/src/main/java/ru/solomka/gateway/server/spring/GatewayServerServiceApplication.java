package ru.solomka.gateway.server.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "ru.solomka.gateway.server")
@EnableEurekaServer
public class GatewayServerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerServiceApplication.class, args);
    }
}