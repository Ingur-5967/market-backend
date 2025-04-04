package ru.solomka.product.comment;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.solomka.product.common.pagination.PaginationFilter;
import ru.solomka.product.common.repository.PaginationJpaEntityRepositoryAdapter;
import ru.solomka.product.common.mapper.Mapper;
import ru.solomka.product.common.pagination.PaginationObject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaCommentEntityRepositoryAdapter extends PaginationJpaEntityRepositoryAdapter<JpaCommentEntity, CommentEntity> implements CommentRepository {

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
    public Optional<CommentEntity> findCommentById(UUID productId) {
        return commentRepository.findCommentById(productId).map(mapper::mapToDomain);
    }

    @Override
    public List<CommentEntity> findCommentsByOwnerId(UUID ownerId) {
        return commentRepository.findAllByUserId(ownerId).stream().map(mapper::mapToDomain).toList();
    }

    @Override
    public PaginationObject<CommentEntity> findAll(int offset, int limit, PaginationFilter filter, String... filteredBy) {
        return super.findByFilter(offset, limit, filter, filteredBy);
    }
}