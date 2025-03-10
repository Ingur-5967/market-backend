package ru.solomka.product.minio;

import java.util.Optional;

public interface MinioComponent {

    void putObject(String object, byte[] bytes);

    Optional<String> getObject(String object);

    void createBucket(String bucket);

    void deleteFile(String object);
}