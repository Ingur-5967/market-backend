package ru.solomka.product;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.common.Entity;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class ProductEntity implements Entity {

    UUID id;

    @NonNull String name;

    @NonNull String description;

    @NonNull String category;

    @NonNull Integer price;

    @NonNull Double rating;

    Instant createdAt;
}