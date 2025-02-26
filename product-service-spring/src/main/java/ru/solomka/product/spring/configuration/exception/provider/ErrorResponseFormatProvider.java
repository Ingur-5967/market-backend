package ru.solomka.product.spring.configuration.exception.provider;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.ErrorResponse;
import ru.solomka.product.spring.configuration.exception.ExceptionFormat;
import ru.solomka.product.spring.configuration.exception.ExceptionFormatProvider;

public class ErrorResponseFormatProvider implements ExceptionFormatProvider {

    @Override
    public @NotNull ExceptionFormat create(@NotNull Exception exception) {
        ErrorResponse errorResponse = (ErrorResponse) exception;
        return new ExceptionFormat(
                errorResponse.getStatusCode().value(),
                exception.getMessage(),
                exception.getClass()
        );
    }

    @Override
    public boolean supports(@NotNull Exception exception) {
        return exception instanceof ErrorResponse;
    }
}