package ru.solomka.product;

import ru.solomka.product.common.EntityRepository;

import java.util.Optional;

public interface ProductRepository extends EntityRepository<ProductEntity> {
    Optional<ProductEntity> findByName(String name);
}