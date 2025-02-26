package ru.solomka.product.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.ProductEntity;
import ru.solomka.product.ProductService;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.exception.EntityNotFoundException;
import ru.solomka.product.cqrs.query.GetProductByNameQuery;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetProductByNameQueryHandler implements CommandHandler<GetProductByNameQuery, ProductEntity> {

    @NonNull ProductService productService;

    @Override
    public ProductEntity handle(GetProductByNameQuery commandEntity) {
        return productService.findByName(commandEntity.getName())
                .orElseThrow(() -> new EntityNotFoundException("Product with name '%s' not found".formatted(commandEntity.getName())));
    }
}