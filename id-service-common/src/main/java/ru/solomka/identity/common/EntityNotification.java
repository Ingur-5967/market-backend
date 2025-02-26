package ru.solomka.identity.common;

public interface EntityNotification<M, E> {
    void notifyCreate(M message, E entity);
    void notifyUpdate(M message, E entity);
    void notifyDelete(M message, E entity);
}