package ru.solomka.product.cqrs.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.common.pagination.PaginationFilter;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetProductsByFilterQuery {

    @NonNull Integer offset;

    @NonNull Integer limit;

    @NonNull PaginationFilter filter;

    @NonNull String sortFieldValue;
}