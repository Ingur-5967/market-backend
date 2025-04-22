package ru.solomka.product.comment.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.ProductEntity;
import ru.solomka.product.ProductService;
import ru.solomka.product.comment.CommentEntity;
import ru.solomka.product.comment.CommentService;
import ru.solomka.product.comment.cqrs.command.CreateCommentCommand;
import ru.solomka.product.comment.exception.CommentAlreadyExistsException;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.exception.EntityNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateCommentCommandHandler implements CommandHandler<CreateCommentCommand, CommentEntity> {

    @NonNull ProductService productService;
    @NonNull CommentService commentService;

    @Override
    public CommentEntity handle(CreateCommentCommand createCommentCommand) {

        if(!productService.existsById(createCommentCommand.getProductId()))
            throw new EntityNotFoundException("Cannot add comment to to the non-existent product");

        commentService.findAllCommentsByOwnerId(createCommentCommand.getUserId())
                .stream()
                .filter(comment -> comment.getProductId().equals(createCommentCommand.getProductId()))
                .findAny()
                .ifPresent(comment -> {
                    throw new CommentAlreadyExistsException(
                            "User with id '%s' already have comment for product with id '%s'"
                                    .formatted(createCommentCommand.getUserId(), createCommentCommand.getProductId())
                    );
                });

        CommentEntity comment = CommentEntity.builder()
                .userId(createCommentCommand.getUserId())
                .productId(createCommentCommand.getProductId())
                .comment(createCommentCommand.getComment())
                .rating(createCommentCommand.getRating())
                .build();

        commentService.create(comment);

        ProductEntity productEntity = productService.findById(createCommentCommand.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot update rating to the non-existent product with id '%s'".formatted(createCommentCommand.getProductId())));

        List<CommentEntity> comments = commentService.findAllCommentsByProductId(productEntity.getId());

        double newRating = Math.round(comments.stream().mapToDouble(CommentEntity::getRating).sum()/comments.size());
        productEntity.setRating(newRating);

        productService.update(productEntity);

        return comment;
    }
}