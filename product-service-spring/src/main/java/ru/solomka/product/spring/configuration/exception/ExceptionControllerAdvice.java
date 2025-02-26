package ru.solomka.product.spring.configuration.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExceptionControllerAdvice {

    @NotNull ExceptionFormatFactory exceptionFormatFactory;

    @ExceptionHandler(Exception.class)
    public @NotNull ResponseEntity<ErrorResponse> handleException(@NotNull Exception exception) {
        ExceptionFormat restExceptionFormat = exceptionFormatFactory.create(exception);
        return ResponseEntity
                .status(restExceptionFormat.getStatusCode())
                .body(new ErrorResponse(Instant.now(), restExceptionFormat));
    }
}