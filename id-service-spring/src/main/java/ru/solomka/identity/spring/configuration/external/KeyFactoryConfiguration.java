package ru.solomka.identity.spring.configuration.external;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;

@Configuration
public class KeyFactoryConfiguration {

    @Bean
    @SneakyThrows
    KeyFactory keyFactory() {
        return KeyFactory.getInstance("RSA");
    }
}