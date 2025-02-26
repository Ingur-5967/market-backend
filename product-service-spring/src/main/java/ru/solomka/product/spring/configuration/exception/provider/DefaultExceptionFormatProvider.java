package ru.solomka.product.spring.configuration.exception.provider;


import org.jetbrains.annotations.NotNull;
import ru.solomka.product.spring.configuration.exception.ExceptionFormat;
import ru.solomka.product.spring.configuration.exception.ExceptionFormatProvider;

public class DefaultExceptionFormatProvider implements ExceptionFormatProvider {

    @Override
    public @NotNull ExceptionFormat create(@NotNull Exception exception) {
        return new ExceptionFormat(
                500,
                exception.getMessage(),
                exception.getClass()
        );
    }

    @Override
    public boolean supports(@NotNull Exception exception) {
        return true;
    }
}