package ru.solomka.identity.spring.configuration.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatusCodeRangeExceptionFormatProvider implements ExceptionFormatProvider {

  int statusCode;

  @NotNull Class<?> superClass;

  @Override
  public @NotNull ExceptionFormat create(@NotNull Exception e) {
    return new ExceptionFormat(
            statusCode,
            e.getMessage(),
            e.getClass()
    );
  }

  @Override
  public boolean supports(@NotNull Exception e) {
    return superClass.isAssignableFrom(e.getClass());
  }
}