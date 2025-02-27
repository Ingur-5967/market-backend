package ru.solomka.identity.spring.configuration.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExceptionFormat {
    @JsonIgnore int statusCode;
    @NotNull String message;
    @JsonIgnore @NotNull Class<? extends Exception> exceptionClass;
}
