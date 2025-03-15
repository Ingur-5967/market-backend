package ru.solomka.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.cqrs.command.CreateProductCommand;
import ru.solomka.product.cqrs.query.GetProductByIdQuery;
import ru.solomka.product.cqrs.query.GetProductByNameQuery;
import ru.solomka.product.request.ProductCreateRequest;

import java.util.UUID;

@Tag(name = "product-endpoints", description = "Product entity management")
@RestController
@RequestMapping("/product/catalog")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRestController {

    @NonNull CommandHandler<GetProductByIdQuery, ProductEntity> productByIdQueryProductEntityCommandHandler;
    @NonNull CommandHandler<GetProductByNameQuery, ProductEntity> productByNameQueryProductEntityCommandHandler;

    @NonNull CommandHandler<CreateProductCommand, ProductEntity> createProductCommandProductEntityCommandHandler;

    @Operation(
            summary = "Get product entity by id",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns product entity",
                    content = { @Content(mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404", description = "Exception: Product entity with current value not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500", description = "Exception: Invalid filter type",
                    content = @Content
            )
    })
    @GetMapping(value = "/search/{filterType}/{searchBy}", produces = "application/json")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable("filterType") String param,
                                                        @PathVariable("searchBy") String value) {
        ProductEntity entity;
        switch (param) {
            case "uuid" -> entity = productByIdQueryProductEntityCommandHandler.handle(new GetProductByIdQuery(UUID.fromString(value)));
            case "name" -> entity = productByNameQueryProductEntityCommandHandler.handle(new GetProductByNameQuery(value));
            default -> throw new IllegalArgumentException("Invalid filter type");
        }
        return ResponseEntity.ok(entity);
    }

    @Operation(
            summary = "Create product entity",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns created product entity",
                    content = { @Content(mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "409", description = "Exception: An entity with such parameters already exists",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500", description = "Exception: Invalid arguments (price/description)",
                    content = @Content
            )
    })
    @SneakyThrows
    @PostMapping(value = "/create-product", produces = "application/json")
    public ResponseEntity<ProductEntity> createProduct(ProductCreateRequest productCreateRequest) {
        ProductEntity productEntity = createProductCommandProductEntityCommandHandler.handle(new CreateProductCommand(
                productCreateRequest.getName(),
                productCreateRequest.getDescription(),
                productCreateRequest.getImage().getBytes(),
                productCreateRequest.getPrice()
        ));
        return ResponseEntity.ok(productEntity);
    }
}