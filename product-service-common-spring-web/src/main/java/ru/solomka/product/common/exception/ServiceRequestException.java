package ru.solomka.product.common.exception;

public class ServiceRequestException extends RuntimeException {
    public ServiceRequestException(String message) {
        super(message);
    }
}
