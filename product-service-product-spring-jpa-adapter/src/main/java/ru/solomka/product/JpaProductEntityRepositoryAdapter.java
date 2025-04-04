package ru.solomka.product;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.solomka.product.common.pagination.PaginationFilter;
import ru.solomka.product.common.repository.PaginationJpaEntityRepositoryAdapter;
import ru.solomka.product.common.mapper.Mapper;
import ru.solomka.product.common.pagination.PaginationObject;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaProductEntityRepositoryAdapter extends PaginationJpaEntityRepositoryAdapter<JpaProductEntity, ProductEntity> implements ProductRepository {

    @NonNull
    JpaProductRepository jpaProductRepository;

    public JpaProductEntityRepositoryAdapter(@NonNull JpaProductRepository repository,
                                             @NonNull Mapper<ProductEntity, JpaProductEntity> mapper) {
        super(repository, mapper);
        this.jpaProductRepository = repository;
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        return jpaProductRepository.findByName(name).map(mapper::mapToDomain);
    }

    @Override
    public PaginationObject<ProductEntity> findAll(int offset, int limit, PaginationFilter filter, String ...filteredBy) {
        return this.findByFilter(offset, limit, filter, filteredBy);
    }
}