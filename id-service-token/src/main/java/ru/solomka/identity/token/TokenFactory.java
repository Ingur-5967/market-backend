package ru.solomka.identity.token;

import ru.solomka.identity.principal.PrincipalEntity;

import java.time.Duration;

public interface TokenFactory {
    TokenEntity create(PrincipalEntity entity, Duration lifetime, TokenType tokenType);
}