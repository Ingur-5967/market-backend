package ru.solomka.product.comment;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.product.comment.cqrs.command.CreateCommentCommand;
import ru.solomka.product.comment.cqrs.query.GetCommentsByIdQuery;
import ru.solomka.product.comment.cqrs.query.GetCommentsByOwnerIdQuery;
import ru.solomka.product.comment.cqrs.query.GetCommentsByProductIdQuery;
import ru.solomka.product.comment.request.PublicationCommentRequest;
import ru.solomka.product.common.cqrs.CommandHandler;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentRestController {

    @NonNull CommandHandler<CreateCommentCommand, CommentEntity> createCommentCommandHandler;

    @NonNull CommandHandler<GetCommentsByProductIdQuery, List<CommentEntity>> getCommentsByProductIdQueryCommandHandler;
    @NonNull CommandHandler<GetCommentsByIdQuery, List<CommentEntity>> getCommentsByIdQueryCommandHandler;
    @NonNull CommandHandler<GetCommentsByOwnerIdQuery, List<CommentEntity>> getCommentsByOwnerIdQueryCommandHandler;

    @GetMapping(value = "/{filterType}/{searchBy}", produces = "application/json")
    public ResponseEntity<List<CommentEntity>> getCommentsByProductId(@PathVariable("filterType") String filterType,
                                                                      @PathVariable("searchBy") UUID id) {
        List<CommentEntity> comments;
        switch (filterType) {
            case "by-product" -> comments = getCommentsByProductIdQueryCommandHandler.handle(new GetCommentsByProductIdQuery(id));
            case "by-owner" -> comments = getCommentsByOwnerIdQueryCommandHandler.handle(new GetCommentsByOwnerIdQuery(id));
            case "by-id" -> comments = getCommentsByIdQueryCommandHandler.handle(new GetCommentsByIdQuery(id));
            default -> throw new IllegalArgumentException("Invalid filter type");
        }
        return ResponseEntity.ok(comments);
    }

    @PostMapping(value = "/publication", produces = "application/json")
    public ResponseEntity<CommentEntity> publishCommentToProduct(@RequestBody PublicationCommentRequest publicationCommentRequest) {
        CommentEntity comment = createCommentCommandHandler.handle(new CreateCommentCommand(
                publicationCommentRequest.getUserId(),
                publicationCommentRequest.getProductId(),
                publicationCommentRequest.getComment(),
                publicationCommentRequest.getRating()
        ));
        return ResponseEntity.ok(comment);
    }
}