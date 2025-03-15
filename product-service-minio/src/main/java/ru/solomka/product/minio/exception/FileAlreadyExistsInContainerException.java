package ru.solomka.product.minio.exception;

public class FileAlreadyExistsInContainerException extends RuntimeException {
    public FileAlreadyExistsInContainerException(String message) {
        super(message);
    }
}
