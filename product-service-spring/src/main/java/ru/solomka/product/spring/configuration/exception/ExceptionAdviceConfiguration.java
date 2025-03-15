package ru.solomka.product.spring.configuration.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.solomka.product.comment.exception.CommentAlreadyExistsException;
import ru.solomka.product.common.exception.EntityAlreadyExistsException;
import ru.solomka.product.common.exception.EntityNotFoundException;
import ru.solomka.product.minio.exception.FileAlreadyExistsInContainerException;
import ru.solomka.product.minio.exception.FileNotExistsInContainerException;
import ru.solomka.product.spring.configuration.exception.provider.DefaultExceptionFormatProvider;
import ru.solomka.product.spring.configuration.exception.provider.ErrorResponseFormatProvider;
import ru.solomka.product.spring.configuration.exception.provider.ProviderExceptionFormat;

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
    ExceptionFormatProvider fileNotExistsInContainerExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                500,
                FileNotExistsInContainerException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider commentAlreadyExistsExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                500,
                CommentAlreadyExistsException.class
        );
    }

    @Bean
    @Order(0)
    ExceptionFormatProvider fileAlreadyExistsInContainerExceptionFormatProvider() {
        return new StatusCodeRangeExceptionFormatProvider(
                500,
                FileAlreadyExistsInContainerException.class
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