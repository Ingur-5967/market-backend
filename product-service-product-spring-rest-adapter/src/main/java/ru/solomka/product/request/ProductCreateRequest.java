package ru.solomka.product.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCreateRequest {

    @NonNull String name;

    @NonNull String description;

    @NonNull Integer price;
}