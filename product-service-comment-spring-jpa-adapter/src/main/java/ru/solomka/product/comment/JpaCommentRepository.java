package ru.solomka.product.comment;

import ru.solomka.product.common.BaseCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaCommentRepository extends BaseCrudRepository<JpaCommentEntity> {
    Optional<JpaCommentEntity> findByUserId(UUID userId);
    List<JpaCommentEntity> findAllByProductId(UUID productId);
    List<JpaCommentEntity> findAllById(UUID id);
    List<JpaCommentEntity> findAllByUserId(UUID userId);
}