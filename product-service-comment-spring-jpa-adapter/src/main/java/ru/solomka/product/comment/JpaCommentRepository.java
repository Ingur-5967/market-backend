package ru.solomka.product.comment;

import ru.solomka.product.common.BaseCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaCommentRepository extends BaseCrudRepository<JpaCommentEntity> {
    List<JpaCommentEntity> findAllByProductId(UUID productId);
    Optional<JpaCommentEntity> findCommentById(UUID id);
    List<JpaCommentEntity> findAllByUserId(UUID userId);
}