package ru.solomka.product.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.ProductEntity;
import ru.solomka.product.ProductService;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.exception.EntityNotFoundException;
import ru.solomka.product.cqrs.query.GetProductByIdQuery;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetProductByIdQueryHandler implements CommandHandler<GetProductByIdQuery, ProductEntity> {

    @NonNull ProductService productService;

    @Override
    public ProductEntity handle(GetProductByIdQuery getProductByIdQuery) {
        return productService.findById(getProductByIdQuery.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id '%s' not found".formatted(getProductByIdQuery.getId())));
    }
}