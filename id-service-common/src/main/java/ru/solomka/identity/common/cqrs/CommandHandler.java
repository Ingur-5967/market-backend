package ru.solomka.identity.common.cqrs;

public interface CommandHandler<C, R> {
    R handle(C commandEntity);
}