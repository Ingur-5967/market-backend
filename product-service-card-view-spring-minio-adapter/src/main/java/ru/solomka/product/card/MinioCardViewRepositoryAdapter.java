package ru.solomka.product.card;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.product.minio.MinioComponent;
import ru.solomka.product.minio.MinioValidator;

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
        minioComponent.uploadFile(entity.getId().toString(), entity.getFile());
        return entity;
    }

    @SneakyThrows
    @Override
    public CardViewEntity update(CardViewEntity entity) {
        if (this.existsById(entity.getId()))
            throw new RuntimeException("File in bucket with name '%s' already exists".formatted(entity.getId().toString()));

        minioComponent.uploadFile(entity.getId().toString(), entity.getFile());
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
        return minioComponent.downloadFile(id.toString());
    }

    @SneakyThrows
    @Override
    public boolean existsById(UUID id) {
        return minioValidator.existsFile(id.toString());
    }
}