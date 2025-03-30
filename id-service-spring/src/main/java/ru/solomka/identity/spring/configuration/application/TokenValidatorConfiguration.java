package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.identity.token.TokenExtractor;
import ru.solomka.identity.token.handler.ExtractAndValidateAccessTokenCommandHandler;

@Configuration
public class TokenValidatorConfiguration {

    @Bean
    ExtractAndValidateAccessTokenCommandHandler extractAndValidateAccessTokenCommandHandler(@NonNull TokenExtractor extractor) {
        return new ExtractAndValidateAccessTokenCommandHandler(extractor);
    }
}