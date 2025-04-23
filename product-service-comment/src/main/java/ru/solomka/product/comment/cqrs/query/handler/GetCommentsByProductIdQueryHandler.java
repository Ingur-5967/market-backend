package ru.solomka.product.comment.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.ProductService;
import ru.solomka.product.comment.CommentEntity;
import ru.solomka.product.comment.CommentService;
import ru.solomka.product.comment.cqrs.query.GetCommentsByProductIdQuery;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.exception.EntityNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetCommentsByProductIdQueryHandler implements CommandHandler<GetCommentsByProductIdQuery, List<CommentEntity>> {

    @NonNull ProductService productService;
    @NonNull CommentService commentService;

    @Override
    public List<CommentEntity> handle(GetCommentsByProductIdQuery commandEntity) {
        if(productService.findById(commandEntity.getProductId()).isEmpty())
            throw new EntityNotFoundException("Cannot load comment because product with id '%s' not found".formatted(commandEntity.getProductId()));

        return commentService.findAllCommentsByProductId(commandEntity.getProductId());
    }
}