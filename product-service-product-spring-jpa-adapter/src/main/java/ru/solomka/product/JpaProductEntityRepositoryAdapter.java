package ru.solomka.product;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.common.BaseCrudRepository;
import ru.solomka.product.common.BaseJpaEntityRepositoryAdapter;
import ru.solomka.product.common.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaProductEntityRepositoryAdapter extends BaseJpaEntityRepositoryAdapter<JpaProductEntity, ProductEntity> implements ProductRepository {

    @NonNull JpaProductRepository jpaProductRepository;

    public JpaProductEntityRepositoryAdapter(@NonNull JpaProductRepository repository,
                                             @NonNull Mapper<ProductEntity, JpaProductEntity> mapper) {
        super(repository, mapper);
        this.jpaProductRepository = repository;
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        return jpaProductRepository.findByName(name).map(mapper::mapToDomain);
    }
}