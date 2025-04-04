package ru.solomka.product.common.pagination;

import lombok.NonNull;
import org.springframework.data.domain.Sort;

public enum PaginationFilter {
    ASC,
    DESC;

    public @NonNull Sort getSortConfiguration(String ...filteredBy) {
        return Sort.by(Sort.Direction.fromString(this.name()), filteredBy);
    }
}