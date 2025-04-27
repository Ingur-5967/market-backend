package ru.solomka.product;

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
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.pagination.PaginationObject;
import ru.solomka.product.cqrs.command.CreateProductCommand;
import ru.solomka.product.cqrs.query.GetProductByIdQuery;
import ru.solomka.product.cqrs.query.GetProductByNameQuery;
import ru.solomka.product.cqrs.query.GetProductsByFilterQuery;
import ru.solomka.product.request.GetProductsPaginationRequest;
import ru.solomka.product.request.ProductCreateRequest;

import java.util.UUID;

@Tag(name = "product-endpoints", description = "Product entity management")
@RestController
@RequestMapping("/product/catalog")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRestController {

    @NonNull CommandHandler<GetProductsByFilterQuery, PaginationObject<ProductEntity>> productsByCategoryQueryCommandHandler;

    @NonNull CommandHandler<GetProductByIdQuery, ProductEntity> productByIdQueryCommandHandler;
    @NonNull CommandHandler<GetProductByNameQuery, ProductEntity> productByNameQueryCommandHandler;

    @NonNull CommandHandler<CreateProductCommand, ProductEntity> createProductCommandHandler;

    @Operation(
            summary = "Get product by filter parameters",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns products entity",
                    content = { @Content(mediaType = "application/json") }
            )
    })
    @GetMapping(value = "/search/filter", produces = "application/json")
    public ResponseEntity<PaginationObject<ProductEntity>> getProductByFilter(GetProductsPaginationRequest getProductsPaginationRequest) {
        PaginationObject<ProductEntity> entityPaginationObject = productsByCategoryQueryCommandHandler.handle(
                new GetProductsByFilterQuery(
                        getProductsPaginationRequest.getOffset(), getProductsPaginationRequest.getLimit(),
                        getProductsPaginationRequest.getFilterType(), getProductsPaginationRequest.getSortBy()
                )
        );
        return ResponseEntity.ok(entityPaginationObject);
    }

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
    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<ProductEntity> getProductByParam(
                                                        @Parameter(
                                                                name = "Field 'category'",
                                                                description = "The type to be searched for",
                                                                required = true,
                                                                examples = {
                                                                        @ExampleObject(name = "By UUID field", value = "uuid"),
                                                                        @ExampleObject(name = "By Name field", value = "name")
                                                                }
                                                        )
                                                        @RequestParam("by") String by,

                                                        @Parameter(
                                                                name = "Field 'value'",
                                                                description = "The value to be searched for",
                                                                required = true,
                                                                examples = {
                                                                        @ExampleObject(name = "uuid value", value = "f8a19d45-5784-4792-8678-64cb7fc0ece1"),
                                                                        @ExampleObject(name = "name value", value = "Увлажняющий крем для рук"),
                                                                }
                                                        )
                                                        @RequestParam("value") String value) {
        ProductEntity entity;
        switch (by) {
            case "uuid" -> entity = productByIdQueryCommandHandler.handle(new GetProductByIdQuery(UUID.fromString(value)));
            case "name" -> entity = productByNameQueryCommandHandler.handle(new GetProductByNameQuery(value));
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
    @PostMapping(value = "/create-product", consumes = "multipart/form-data")
    public ResponseEntity<ProductEntity> createProduct(ProductCreateRequest productCreateRequest) {
        ProductEntity productEntity = createProductCommandHandler.handle(new CreateProductCommand(
                productCreateRequest.getName(),
                productCreateRequest.getDescription(),
                productCreateRequest.getCategory(),
                productCreateRequest.getImage().getBytes(),
                productCreateRequest.getPrice()
        ));
        return ResponseEntity.ok(productEntity);
    }
}