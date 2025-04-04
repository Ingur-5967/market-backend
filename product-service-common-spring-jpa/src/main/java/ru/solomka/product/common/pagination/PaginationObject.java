package ru.solomka.product.common.pagination;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.common.Entity;

import java.util.List;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaginationObject<T extends Entity>  {

    List<T> content;

    int totalPages;

    int currentPage;

    long totalElementsOnPage;
}