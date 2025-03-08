package ru.solomka.product;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.cqrs.command.CreateProductCommand;
import ru.solomka.product.cqrs.query.GetProductByIdQuery;
import ru.solomka.product.cqrs.query.GetProductByNameQuery;
import ru.solomka.product.request.ProductCreateRequest;

import java.util.UUID;

@RestController
@RequestMapping("/product/catalog")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRestController {

    @NonNull CommandHandler<GetProductByIdQuery, ProductEntity> productByIdQueryProductEntityCommandHandler;
    @NonNull CommandHandler<GetProductByNameQuery, ProductEntity> productByNameQueryProductEntityCommandHandler;

    @NonNull CommandHandler<CreateProductCommand, ProductEntity> createProductCommandProductEntityCommandHandler;

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

    @SneakyThrows
    @PostMapping(value = "/create-product", produces = {MediaType.APPLICATION_JSON_VALUE})
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