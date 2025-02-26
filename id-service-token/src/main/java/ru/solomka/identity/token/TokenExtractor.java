package ru.solomka.identity.token;

public interface TokenExtractor {
    TokenEntity extract(String token);
}