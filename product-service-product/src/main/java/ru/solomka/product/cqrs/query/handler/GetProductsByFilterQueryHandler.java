package ru.solomka.product.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.ProductEntity;
import ru.solomka.product.ProductService;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.pagination.PaginationObject;
import ru.solomka.product.cqrs.query.GetProductsByFilterQuery;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetProductsByFilterQueryHandler implements CommandHandler<GetProductsByFilterQuery, PaginationObject<ProductEntity>> {

    @NonNull ProductService productService;

    @Override
    public PaginationObject<ProductEntity> handle(GetProductsByFilterQuery commandEntity) {
        return productService.findByFilter(
                commandEntity.getOffset(),
                commandEntity.getLimit(),
                commandEntity.getFilter(),
                commandEntity.getSortFieldValue());
    }
}