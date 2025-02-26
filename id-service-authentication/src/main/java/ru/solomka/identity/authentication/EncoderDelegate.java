package ru.solomka.identity.authentication;

import org.jetbrains.annotations.NotNull;

public interface EncoderDelegate {
    String encode(String value);
    boolean matches(@NotNull String value, @NotNull String encryptedValue);
}