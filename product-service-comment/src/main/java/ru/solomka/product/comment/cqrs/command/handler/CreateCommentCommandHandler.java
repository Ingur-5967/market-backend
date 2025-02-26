package ru.solomka.product.comment.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.ProductService;
import ru.solomka.product.comment.CommentEntity;
import ru.solomka.product.comment.CommentService;
import ru.solomka.product.comment.cqrs.command.CreateCommentCommand;
import ru.solomka.product.comment.exception.CommentException;
import ru.solomka.product.common.cqrs.CommandHandler;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateCommentCommandHandler implements CommandHandler<CreateCommentCommand, CommentEntity> {

    @NonNull ProductService productService;
    @NonNull CommentService commentService;

    @Override
    public CommentEntity handle(CreateCommentCommand createCommentCommand) {
        if(commentService.findByCommentOwnerId(createCommentCommand.getUserId()).isPresent())
            throw new CommentException(
                    "User with id '%s' already have comment for product with id '%s'"
                            .formatted(createCommentCommand.getUserId(), createCommentCommand.getProductId())
            );

        if(!productService.existsById(createCommentCommand.getProductId()))
            throw new CommentException("Cannot add comment to to the non-existent product");

        CommentEntity comment = CommentEntity.builder()
                .productId(createCommentCommand.getProductId())
                .comment(createCommentCommand.getComment())
                .rating(createCommentCommand.getRating())
                .build();

        return commentService.create(comment);
    }
}