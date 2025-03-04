package ru.solomka.product.minio;

public interface MinioValidator {
    boolean existsBucket(String bucket);
    boolean existsFile(String object);
}