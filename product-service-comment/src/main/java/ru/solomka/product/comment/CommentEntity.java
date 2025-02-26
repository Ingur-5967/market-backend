package ru.solomka.product.comment;

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
public class CommentEntity implements Entity {

    UUID id;

    @NonNull UUID userId;

    @NonNull UUID productId;

    @NonNull String comment;

    @NonNull Double rating;

    Instant createdAt;
}