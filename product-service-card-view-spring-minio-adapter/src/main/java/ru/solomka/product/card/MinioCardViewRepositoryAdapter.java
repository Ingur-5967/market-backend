package ru.solomka.product.card;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.product.minio.MinioComponent;
import ru.solomka.product.minio.MinioValidator;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MinioCardViewRepositoryAdapter implements CardViewRepository {

    @NotNull MinioComponent minioComponent;
    @NotNull MinioValidator minioValidator;

    @SneakyThrows
    @Override
    public CardViewEntity create(CardViewEntity entity) {
        minioComponent.putObject(entity.getId().toString(), entity.getImageBytes());
        return entity;
    }

    @SneakyThrows
    @Override
    public CardViewEntity update(CardViewEntity entity) {
        if (this.existsById(entity.getId()))
            throw new RuntimeException("File in bucket with name '%s' already exists".formatted(entity.getId().toString()));

        minioComponent.putObject(entity.getId().toString(), entity.getImageBytes());
        return entity;
    }

    @Override
    public CardViewEntity deleteById(UUID id) {
        CardViewEntity deletedCardView = this.findById(id).orElseThrow(RuntimeException::new);
        minioComponent.deleteFile(deletedCardView.getId().toString());
        return deletedCardView;
    }

    @SneakyThrows
    @Override
    public Optional<CardViewEntity> findById(UUID id) {
        if(!this.existsById(id))
            throw new RuntimeException("File in bucket with name '%s' does not exist".formatted(id.toString()));

        InputStream is = minioComponent.getObject(id.toString()).orElseThrow(RuntimeException::new);

        return Optional.of(CardViewEntity.builder().id(id).imageBytes(is.readAllBytes()).createdAt(Instant.now()).build());
    }

    @SneakyThrows
    @Override
    public boolean existsById(UUID id) {
        return minioValidator.existsFile(id.toString());
    }
}