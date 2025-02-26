package ru.solomka.identity.spring.configuration.exception;

import org.apache.hc.core5.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.solomka.identity.authentication.exception.AuthenticationException;
import ru.solomka.identity.common.exception.EntityAlreadyExistsException;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.spring.configuration.exception.provider.DefaultExceptionFormatProvider;
import ru.solomka.identity.spring.configuration.exception.provider.ErrorResponseFormatProvider;
import ru.solomka.identity.spring.configuration.exception.provider.ProviderExceptionFormat;
import ru.solomka.identity.token.exception.TokenException;
import ru.solomka.identity.token.exception.TokenExpiredException;

import java.util.List;

@Configuration
public class ExceptionAdviceConfiguration {
    @Bean
    @Order(2)
    ExceptionFormatProvider defaultExceptionFormatProvider() {
        return new DefaultExceptionFormatProvider();
    }

    @Bean
    @Order(1)
    ExceptionFormatProvider errorResponseExceptionFormatProvider() {
        return new ErrorResponseFormatProvider();
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider exceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                HttpStatus.SC_FORBIDDEN,
                TokenException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider authenticationExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                HttpStatus.SC_BAD_REQUEST,
                AuthenticationException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider entityNotFoundExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                404,
                EntityNotFoundException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider enityAlreadyExistsExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                409,
                EntityAlreadyExistsException.class
        );
    }

    @Bean
    ExceptionFormatFactory exceptionFormatFactory(
            @NotNull List<ExceptionFormatProvider> exceptionFormatProviders
    ) {
        return new ProviderExceptionFormat(exceptionFormatProviders);
    }
}