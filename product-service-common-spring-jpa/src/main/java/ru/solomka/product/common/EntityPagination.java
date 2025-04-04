package ru.solomka.product.common;

import ru.solomka.product.common.pagination.PaginationFilter;
import ru.solomka.product.common.pagination.PaginationObject;

import java.util.function.Supplier;

public interface EntityPagination<E extends Entity, F> {
    PaginationObject<E> findByFilter(int offset,
                                     int limit,
                                     PaginationFilter paginationFilter,
                                     String ...filteredBy);
}