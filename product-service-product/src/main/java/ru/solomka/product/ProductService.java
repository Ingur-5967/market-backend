package ru.solomka.product;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.product.common.EntityService;
import ru.solomka.product.common.pagination.PaginationFilter;
import ru.solomka.product.common.pagination.PaginationObject;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService extends EntityService<ProductEntity> {

    @NonNull ProductRepository repository;

    public ProductService(@NonNull ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public @NotNull ProductEntity create(@NotNull ProductEntity entity) {
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now());
        return repository.create(entity);
    }

    public Optional<ProductEntity> findByName(@NonNull String name) {
        return repository.findByName(name);
    }

    public PaginationObject<ProductEntity> findByFilter(int offset, int limit, PaginationFilter filter, String ...filteredBy) {
        return repository.findAll(offset, limit, filter, filteredBy);
    }
}