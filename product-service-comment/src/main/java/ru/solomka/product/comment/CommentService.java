package ru.solomka.product.comment;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.product.common.EntityService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService extends EntityService<CommentEntity> {

    @NonNull CommentRepository commentRepository;

    public CommentService(@NonNull CommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
    }

    @Override
    public @NotNull CommentEntity create(@NotNull CommentEntity entity) {
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now());
        return commentRepository.create(entity);
    }

    public List<CommentEntity> findAllCommentsByProductId(@NotNull UUID productId) {
        return commentRepository.findCommentsByProductId(productId);
    }

    public List<CommentEntity> findAllCommentsById(@NotNull UUID id) {
        return commentRepository.findCommentsById(id);
    }

    public List<CommentEntity> findAllCommentsByOwnerId(@NotNull UUID ownerId) {
        return commentRepository.findCommentsByOwnerId(ownerId);
    }
}