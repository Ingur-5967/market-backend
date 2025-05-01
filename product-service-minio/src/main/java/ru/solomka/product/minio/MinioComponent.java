package ru.solomka.product.minio;

import java.io.InputStream;
import java.util.Optional;

public interface MinioComponent {

    void putObject(String object, byte[] bytes);

    Optional<InputStream> getObject(String object);

    void createBucket(String bucket);

    void deleteFile(String object);
}