package ru.solomka.product.comment.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.comment.CommentEntity;
import ru.solomka.product.comment.CommentService;
import ru.solomka.product.comment.cqrs.query.GetCommentByIdQuery;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.exception.EntityNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetCommentByIdQueryHandler implements CommandHandler<GetCommentByIdQuery, CommentEntity> {

    @NonNull CommentService commentService;

    @Override
    public CommentEntity handle(GetCommentByIdQuery commandEntity) {
        return commentService.findCommentById(commandEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException("Comment with id '%s' not found".formatted(commandEntity.getId())));
    }
}