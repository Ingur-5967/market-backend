package ru.solomka.product.comment;

import ru.solomka.product.common.mapper.Mapper;

public class JpaCommentEntityCommentEntityMapper implements Mapper<CommentEntity, JpaCommentEntity> {

    @Override
    public CommentEntity mapToDomain(JpaCommentEntity infrastructureEntity) {
        return CommentEntity.builder()
                .id(infrastructureEntity.getId())
                .userId(infrastructureEntity.getUserId())
                .productId(infrastructureEntity.getProductId())
                .comment(infrastructureEntity.getComment())
                .rating(infrastructureEntity.getRating())
                .createdAt(infrastructureEntity.getCreatedAt()).build();
    }

    @Override
    public JpaCommentEntity mapToInfrastructure(CommentEntity domainEntity) {
        return JpaCommentEntity.builder()
                .id(domainEntity.getId())
                .userId(domainEntity.getUserId())
                .productId(domainEntity.getProductId())
                .comment(domainEntity.getComment())
                .rating(domainEntity.getRating())
                .createdAt(domainEntity.getCreatedAt()).build();
    }
}
