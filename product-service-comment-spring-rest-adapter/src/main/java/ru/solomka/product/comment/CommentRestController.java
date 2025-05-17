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

    @NonNull CommandHandler<CreateCommentCommand, CommentEntity> createCommentCommandHandler;

    @NonNull CommandHandler<GetCommentsByProductIdQuery, List<CommentEntity>> getCommentsByProductIdQueryCommandHandler;

    @Operation(
            summary = "Get all comments by product id",
            method = "GET",
            parameters = {
                    @Parameter(
                            name = "productId",
                            examples = {
                                    @ExampleObject(name = "Product ID value", description = "Example: f8a19d45-5784-4792-8678-64cb7fc0ece1"),
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
    })
    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<List<CommentEntity>> getCommentsByProductId(@RequestParam("productId") UUID productId) {
        List<CommentEntity> comments = getCommentsByProductIdQueryCommandHandler.handle(new GetCommentsByProductIdQuery(productId));;
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