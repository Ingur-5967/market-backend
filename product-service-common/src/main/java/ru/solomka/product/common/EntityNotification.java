package ru.solomka.product.common;

public interface EntityNotification<M, E> {
    void receiveNotifyCreated(M message, E entity);
    void receiveNotifyUpdated(M message, E entity);
    void receiveNotifyDeleted(M message, E entity);
}