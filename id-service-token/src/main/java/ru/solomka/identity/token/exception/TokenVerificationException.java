package ru.solomka.identity.token.exception;

public class TokenVerificationException extends TokenException {
    public TokenVerificationException(String message) {
        super(message);
    }
}