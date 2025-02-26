package ru.solomka.identity.spring.configuration.exception;

import org.jetbrains.annotations.NotNull;

public interface ExceptionFormatProvider {
    @NotNull ExceptionFormat create(@NotNull Exception exception);

    boolean supports(@NotNull Exception exception);
}
