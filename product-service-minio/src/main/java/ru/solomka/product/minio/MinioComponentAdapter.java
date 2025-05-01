package ru.solomka.product.minio;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MinioComponentAdapter implements MinioComponent, MinioValidator {

    @NonNull MinioClient client;

    @NonNull String bucketName;

    @SneakyThrows
    @Override
    public void putObject(String object, byte[] bytes) {
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
        String mimeType = URLConnection.guessContentTypeFromStream(is);

        if(mimeType == null)
            throw new RuntimeException("Mime type cannot be null");

        List<String> validExtensionFile = List.of("jpeg", "jpg", "png");
        if(!validExtensionFile.contains(mimeType.split("/")[1]))
            throw new IllegalArgumentException("Invalid file mime type: %s".formatted(mimeType));

        if(!this.existsBucket(this.bucketName)) this.createBucket(this.bucketName);

        client.putObject(PutObjectArgs.builder()
                .bucket(this.bucketName)
                .object(object)
                .contentType(mimeType)
                .stream(new ByteArrayInputStream(bytes), -1, 10485760).build());
    }

    @SneakyThrows
    @Override
    public Optional<InputStream> getObject(String object) {
        return Optional.ofNullable(client.getObject(GetObjectArgs.builder().bucket(this.bucketName).object(object).build()));
    }

    @SneakyThrows
    @Override
    public void createBucket(String bucket) {
        client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
    }

    @SneakyThrows
    @Override
    public void deleteFile(String object) {
        client.removeObject(RemoveObjectArgs.builder().bucket(this.bucketName).object(object).build());
    }

    @SneakyThrows
    @Override
    public boolean existsBucket(String bucket) {
        return client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
    }

    @Override
    public boolean existsFile(String object) {
        Iterator<Result<Item>> iterator = client.listObjects(ListObjectsArgs.builder().bucket(this.bucketName).build()).iterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false).anyMatch(itemResult -> {
            try {
                return itemResult.get().objectName().equals(object);
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                     XmlParserException e) {
                throw new RuntimeException(e);
            }
        });
    }
}