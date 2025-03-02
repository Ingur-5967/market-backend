package ru.solomka.product.comment;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.common.BaseJpaEntityRepositoryAdapter;
import ru.solomka.product.common.mapper.Mapper;

import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaCommentEntityRepositoryAdapter extends BaseJpaEntityRepositoryAdapter<JpaCommentEntity, CommentEntity> implements CommentRepository {

    @NonNull JpaCommentRepository commentRepository;

    public JpaCommentEntityRepositoryAdapter(@NonNull JpaCommentRepository repository,
                                             @NonNull Mapper<CommentEntity, JpaCommentEntity> mapper) {
        super(repository, mapper);
        this.commentRepository = repository;
    }

    @Override
    public List<CommentEntity> findCommentsByProductId(UUID productId) {
        return commentRepository.findAllByProductId(productId).stream().map(mapper::mapToDomain).toList();
    }

    @Override
    public List<CommentEntity> findCommentsById(UUID productId) {
        return commentRepository.findAllById(productId).stream().map(mapper::mapToDomain).toList();
    }

    @Override
    public List<CommentEntity> findCommentsByOwnerId(UUID ownerId) {
        return commentRepository.findAllByUserId(ownerId).stream().map(mapper::mapToDomain).toList();
    }
}