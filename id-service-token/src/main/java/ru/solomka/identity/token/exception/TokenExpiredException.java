package ru.solomka.identity.token.exception;

public class TokenExpiredException extends TokenException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
