package ru.solomka.product.exception;

public class ProductOperationException extends RuntimeException {
    public ProductOperationException(String message) {
        super(message);
    }
}
