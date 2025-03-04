package ru.solomka.product.minio;

import java.io.InputStream;
import java.util.Optional;

public interface MinioComponent {

    void uploadFile(String object, byte[] bytes);

    <F> Optional<F> downloadFile(String object);

    void createBucket(String bucket);

    void deleteFile(String object);
}