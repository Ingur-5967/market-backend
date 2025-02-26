package ru.solomka.product.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JpaCommentEntity {

    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    @NonNull UUID userId;

    @Column(name = "product_id", nullable = false, updatable = false)
    @NonNull UUID productId;

    @Column(name = "comment", nullable = false, updatable = false)
    @NonNull String comment;

    @Column(name = "rating", nullable = false, updatable = false)
    @NonNull Double rating;

    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;
}