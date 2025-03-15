package ru.solomka.product.minio.exception;

public class FileNotExistsInContainerException extends RuntimeException {
    public FileNotExistsInContainerException(String message) {
        super(message);
    }
}
