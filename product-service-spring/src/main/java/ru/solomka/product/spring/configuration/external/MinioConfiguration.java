package ru.solomka.product.spring.configuration.external;

import io.minio.MinioClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.product.minio.MinioComponentAdapter;

@Getter
@Configuration
public class MinioConfiguration {

    @Value("${service.minio.endpoint}")
    private String endpoint;

    @Value("${service.minio.root-user}")
    private String rootUser;

    @Value("${service.minio.root-password}")
    private String rootPassword;

    @Qualifier("minio-bucket-name")
    @Value("${service.minio.bucket}")
    private String bucketName;

    @Bean
    MinioClient clientBuilder() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(rootUser, rootPassword)
                .build();
    }

    @Bean
    MinioComponentAdapter minioComponentAdapter(MinioClient minioClient) {
        return new MinioComponentAdapter(minioClient, bucketName);
    }
}