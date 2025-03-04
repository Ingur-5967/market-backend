package ru.solomka.product.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.product.*;
import ru.solomka.product.comment.*;
import ru.solomka.product.comment.cqrs.command.handler.CreateCommentCommandHandler;
import ru.solomka.product.comment.cqrs.query.handler.GetCommentByIdQueryHandler;
import ru.solomka.product.comment.cqrs.query.handler.GetCommentsByOwnerIdQueryHandler;
import ru.solomka.product.comment.cqrs.query.handler.GetCommentsByProductIdQueryHandler;
import ru.solomka.product.common.mapper.Mapper;

@Configuration
@EntityScan(basePackageClasses = JpaCommentEntity.class)
@EnableJpaRepositories(repositoryBaseClass = JpaCommentRepository.class)
public class CommentConfiguration {

    @Bean
    CommentRepository commentRepository(@NonNull Mapper<CommentEntity, JpaCommentEntity> commentEntityJpaCommentEntityMapper,
                                        @NonNull JpaCommentRepository jpaCommentRepository) {
        return new JpaCommentEntityRepositoryAdapter(
                jpaCommentRepository,
                commentEntityJpaCommentEntityMapper
        );
    }

    @Bean
    CommentService commentService(@NonNull CommentRepository commentRepository) {
        return new CommentService(commentRepository);
    }

    @Bean
    GetCommentByIdQueryHandler getCommentByIdQueryHandler(@NonNull CommentService commentService) {
        return new GetCommentByIdQueryHandler(commentService);
    }

    @Bean
    GetCommentsByProductIdQueryHandler getCommentsByProductIdQueryHandler(@NonNull CommentService commentService) {
        return new GetCommentsByProductIdQueryHandler(commentService);
    }

    @Bean
    GetCommentsByOwnerIdQueryHandler getCommentByOwnerIdQueryHandler(@NonNull CommentService commentService) {
        return new GetCommentsByOwnerIdQueryHandler(commentService);
    }

    @Bean
    CreateCommentCommandHandler createCommentCommandHandler(@NonNull ProductService productService,
                                                            @NonNull CommentService commentService) {
        return new CreateCommentCommandHandler(productService, commentService);
    }

    @Bean
    JpaCommentEntityCommentEntityMapper jpaCommentEntityCommentEntityMapper() {
        return new JpaCommentEntityCommentEntityMapper();
    }
}
