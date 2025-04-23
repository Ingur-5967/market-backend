package ru.solomka.product.common;

public interface WebRequestSender<T, A> {
    T getResponse(Class<T> responseType, A args);
}