package ru.solomka.product.card;

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
public class CardViewEntity implements Entity {

    @NonNull UUID id;

    byte[] imageBytes;

    @NonNull Instant createdAt;
}