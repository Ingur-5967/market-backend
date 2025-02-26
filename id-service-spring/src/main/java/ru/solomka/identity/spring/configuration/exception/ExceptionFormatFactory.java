package ru.solomka.identity.spring.configuration.exception;

import org.jetbrains.annotations.NotNull;

public interface ExceptionFormatFactory {
    @NotNull ExceptionFormat create(@NotNull Exception var1);
}
