package ru.solomka.product.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.common.pagination.PaginationFilter;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetProductsPaginationRequest {

    @NonNull Integer offset;

    @NonNull Integer limit;

    @NonNull String filterType;

    @NonNull String[] sortBy;
}