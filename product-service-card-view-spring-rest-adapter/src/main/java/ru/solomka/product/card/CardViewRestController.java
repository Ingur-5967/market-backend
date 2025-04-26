package ru.solomka.product.card;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solomka.product.card.cqrs.command.PutImageCardCommand;
import ru.solomka.product.card.cqrs.query.GetImageCardByIdQuery;
import ru.solomka.product.card.response.CardViewResponse;
import ru.solomka.product.common.cqrs.CommandHandler;

import java.util.UUID;

@Tag(name = "cardview-endpoints", description = "Management preview images for products")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/product/source")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardViewRestController {

    @NonNull CommandHandler<PutImageCardCommand, CardViewEntity> putImageCardCommandHandler;

    @NonNull CommandHandler<GetImageCardByIdQuery, byte[]> getImageCardByIdQueryHandler;

    @Operation(
            summary = "Download an image from the minio container in byte representation",
            method = "GET",
            parameters = {
                    @Parameter(
                            name = "Field 'id'",
                            description = "The ID of the product you want to get a picture of the product from",
                            examples = {
                                    @ExampleObject(name = "id", description = "f8a19d45-5784-4792-8678-64cb7fc0ece1")
                            }
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns image in byte representation",
                    content = { @Content(mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "500", description = "Exception: File not found in container with current id",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404", description = "Exception: Product entity with current id not found",
                    content = @Content
            )
    })
    @SneakyThrows
    @GetMapping(value = "/{productId}", produces = "application/json")
    public ResponseEntity<CardViewResponse> getCardFileSource(@PathVariable("productId") UUID id) {
        byte[] image = getImageCardByIdQueryHandler.handle(new GetImageCardByIdQuery(id));
        return ResponseEntity.ok(new CardViewResponse(image));
    }

    @Operation(
            summary = "Upload an image in the minio container",
            method = "POST",
            parameters = {
                    @Parameter(
                            name = "Field 'id'",
                            description = "The ID of the product you want to upload a picture of the product to",
                            examples = {
                                    @ExampleObject(name = "id", description = "f8a19d45-5784-4792-8678-64cb7fc0ece1")
                            }
                    ),
                    @Parameter(
                            name = "Field 'file'",
                            description = "The image file that you want to send to the container"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns card view entity schema",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CardViewEntity.class)) }
            ),
            @ApiResponse(
                    responseCode = "500", description = "Exception: File already exists in container with current id",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404", description = "Exception: Product entity with current id not found",
                    content = @Content
            )
    })
    @SneakyThrows
    @PostMapping(value = "/{productId}", produces = "application/json")
    public ResponseEntity<CardViewEntity> getCardFileSource(@PathVariable("productId") UUID id,
                                                            @ParameterObject @RequestBody MultipartFile file) {
        CardViewEntity cardViewEntity = putImageCardCommandHandler.handle(new PutImageCardCommand(id, file.getBytes()));
        return ResponseEntity.ok(cardViewEntity);
    }
}