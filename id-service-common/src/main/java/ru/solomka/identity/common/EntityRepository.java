package ru.solomka.identity.common;

import java.util.Optional;
import java.util.UUID;

public interface EntityRepository<T extends Entity> {
    T create(T entity);
    T update(T entity);
    T deleteById(UUID id);
    Optional<T> findById(UUID id);

    boolean existsById(UUID id);
}