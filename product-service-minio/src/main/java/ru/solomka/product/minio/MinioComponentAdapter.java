package ru.solomka.product.minio;


import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MinioComponentAdapter implements MinioComponent, MinioValidator{

    @NonNull MinioClient client;

    @Qualifier("minio-bucket-name")
    @NonNull String bucketName;

    @SneakyThrows
    @Override
    public void uploadFile(String object, byte[] bytes) {
        if(!this.existsBucket(bucketName)) this.createBucket(this.bucketName);
        client.putObject(PutObjectArgs.builder().bucket(this.bucketName).object(object).stream(new ByteArrayInputStream(bytes), -1, 10485760).build()
        );
    }

    @SneakyThrows
    @Override
    public <F> Optional<F> downloadFile(String object) {
        InputStream stream = client.getObject(GetObjectArgs.builder().bucket(bucketName).object(object).build());
        return Optional.of(SerializationUtils.deserialize(stream.readAllBytes()));
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