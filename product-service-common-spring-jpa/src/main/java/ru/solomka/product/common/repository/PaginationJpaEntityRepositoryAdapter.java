package ru.solomka.product.common.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.solomka.product.common.BaseCrudRepository;
import ru.solomka.product.common.Entity;
import ru.solomka.product.common.EntityPagination;
import ru.solomka.product.common.mapper.Mapper;
import ru.solomka.product.common.pagination.PaginationFilter;
import ru.solomka.product.common.pagination.PaginationObject;

public abstract class PaginationJpaEntityRepositoryAdapter<I, E extends Entity> extends BaseJpaEntityRepositoryAdapter<I, E> implements EntityPagination<E, PageRequest> {

    public PaginationJpaEntityRepositoryAdapter(@NonNull BaseCrudRepository<I> repository,
                                                @NonNull Mapper<E, I> mapper) {
        super(repository, mapper);
    }

    @Override
    public PaginationObject<E> findByFilter(int offset, int limit, PaginationFilter filter, String ...filteredBy) {
        Page<I> entityPage = repository.findAll(PageRequest.of(offset, limit, filter.getSortConfiguration(filteredBy)));
        return new PaginationObject<>(
                entityPage.getContent().stream().map(mapper::mapToDomain).toList(),
                entityPage.getTotalPages(),
                entityPage.getNumber(),
                entityPage.getTotalElements()
        );
    }
}