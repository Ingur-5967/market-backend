package ru.solomka.product.common.cqrs;

public interface CommandHandler<C, R> {
    R handle(C commandEntity);
}