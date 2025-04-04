package ru.solomka.product;

import ru.solomka.product.common.EntityRepository;
import ru.solomka.product.common.pagination.PaginationFilter;
import ru.solomka.product.common.pagination.PaginationObject;

import java.util.Optional;

public interface ProductRepository extends EntityRepository<ProductEntity> {
    Optional<ProductEntity> findByName(String name);
    PaginationObject<ProductEntity> findAll(int offset, int limit, PaginationFilter filter, String ...filteredBy);
}