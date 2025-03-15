package ru.solomka.product.comment.exception;

public class CommentAlreadyExistsException extends RuntimeException {
    public CommentAlreadyExistsException(String message) {
        super(message);
    }
}
