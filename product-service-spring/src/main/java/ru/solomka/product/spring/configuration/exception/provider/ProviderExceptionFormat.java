package ru.solomka.product.spring.configuration.exception.provider;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.product.spring.configuration.exception.ExceptionFormat;
import ru.solomka.product.spring.configuration.exception.ExceptionFormatFactory;
import ru.solomka.product.spring.configuration.exception.ExceptionFormatProvider;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProviderExceptionFormat implements ExceptionFormatFactory {

    @NotNull List<ExceptionFormatProvider> providers;

    public @NotNull ExceptionFormat create(@NotNull Exception exception) {
        return this.providers.stream()
                .filter((p) -> p.supports(exception))
                .findFirst()
                .map((p) -> p.create(exception))
                .orElseThrow(() -> new IllegalStateException("No supported provider found for: %s".formatted(exception.getClass().getSimpleName())));
    }
}