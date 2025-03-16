package ru.solomka.product.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.product.comment.cqrs.command.CreateCommentCommand;
import ru.solomka.product.comment.cqrs.query.GetCommentByIdQuery;
import ru.solomka.product.comment.cqrs.query.GetCommentsByOwnerIdQuery;
import ru.solomka.product.comment.cqrs.query.GetCommentsByProductIdQuery;
import ru.solomka.product.comment.request.PublicationCommentRequest;
import ru.solomka.product.common.cqrs.CommandHandler;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Tag(name = "comment-endpoints", description = "Product comment management")
@RestController
@RequestMapping("/product/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentRestController {

    @NonNull
    CommandHandler<CreateCommentCommand, CommentEntity> createCommentCommandHandler;

    @NonNull
    CommandHandler<GetCommentsByProductIdQuery, List<CommentEntity>> getCommentsByProductIdQueryCommandHandler;
    @NonNull
    CommandHandler<GetCommentByIdQuery, CommentEntity> getCommentByIdQueryCommandHandler;
    @NonNull
    CommandHandler<GetCommentsByOwnerIdQuery, List<CommentEntity>> getCommentsByOwnerIdQueryCommandHandler;

    @Operation(
            summary = "Get all comments by id",
            method = "GET",
            parameters = {
                    @Parameter(
                            name = "Field 'filterType'",
                            examples = {
                                    @ExampleObject(name = "by-product", description = "Product ID search"),
                                    @ExampleObject(name = "by-owner", description = "Owner comment ID search"),
                                    @ExampleObject(value = "by-id", description = "Comment ID search")
                            }
                    ),
                    @Parameter(
                            name = "Field 'id'",
                            examples = {
                                    @ExampleObject(name = "Product ID value", description = "f8a19d45-5784-4792-8678-64cb7fc0ece1"),
                                    @ExampleObject(name = "Owner ID value", description = "94fc559f-5fa3-464a-8471-1a2c1dcdfc76"),
                                    @ExampleObject(name = "Comment ID value", description = "1753a2ab-05d9-4249-b878-db1ec915c03f")
                            }
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns list of comment entity ",
                    content = {@Content(mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404", description = "Exception: Product entity with current id not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500", description = "Exception: Invalid filter type",
                    content = @Content
            )
    })
    @GetMapping(value = "/{filterType}/{searchBy}", produces = "application/json")
    public ResponseEntity<List<CommentEntity>> getCommentsByProductId(@PathVariable("filterType") String filterType,
                                                                      @PathVariable("searchBy") UUID id) {
        List<CommentEntity> comments;
        switch (filterType) {
            case "by-product" -> comments = getCommentsByProductIdQueryCommandHandler.handle(new GetCommentsByProductIdQuery(id));
            case "by-owner" -> comments = getCommentsByOwnerIdQueryCommandHandler.handle(new GetCommentsByOwnerIdQuery(id));
            case "by-id" -> comments = Collections.singletonList(getCommentByIdQueryCommandHandler.handle(new GetCommentByIdQuery(id)));
            default -> throw new IllegalArgumentException("Invalid filter type");
        }
        return ResponseEntity.ok(comments);
    }

    @Operation(
            summary = "Posting a comment for a product by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns comment entity",
                    content = {@Content(mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404", description = "Exception: Product entity with current id not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500", description = "Exception: A comment for this product from the specified user already exists ",
                    content = @Content
            )
    })
    @PostMapping(value = "/publication", produces = "application/json")
    public ResponseEntity<CommentEntity> publishCommentToProduct(@ParameterObject @RequestBody PublicationCommentRequest publicationCommentRequest) {
        CommentEntity comment = createCommentCommandHandler.handle(new CreateCommentCommand(
                publicationCommentRequest.getUserId(),
                publicationCommentRequest.getProductId(),
                publicationCommentRequest.getComment(),
                publicationCommentRequest.getRating()
        ));
        return ResponseEntity.ok(comment);
    }
}