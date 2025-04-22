package ru.solomka.order.common.exception;

public class ServiceRequestException extends RuntimeException {
    public ServiceRequestException(String message) {
        super(message);
    }
}
