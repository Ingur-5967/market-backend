package ru.solomka.product.comment;

import ru.solomka.product.common.EntityRepository;
import ru.solomka.product.common.pagination.PaginationFilter;
import ru.solomka.product.common.pagination.PaginationObject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends EntityRepository<CommentEntity> {
    List<CommentEntity> findCommentsByProductId(UUID productId);
    Optional<CommentEntity> findCommentById(UUID id);
    List<CommentEntity> findCommentsByOwnerId(UUID ownerId);

    PaginationObject<CommentEntity> findAll(int offset, int limit, PaginationFilter filter, String ...filteredBy);
}