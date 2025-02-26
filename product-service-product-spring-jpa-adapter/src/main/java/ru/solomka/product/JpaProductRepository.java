package ru.solomka.product;

import ru.solomka.product.common.BaseCrudRepository;

import java.util.Optional;

public interface JpaProductRepository extends BaseCrudRepository<JpaProductEntity> {
    Optional<JpaProductEntity> findByName(String name);
}