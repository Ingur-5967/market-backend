package ru.solomka.product;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JpaProductEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @NonNull UUID id;

    @Column(name = "image_container_id", nullable = false, updatable = false)
    @NonNull UUID imageContainerId;

    @Column(name = "name", nullable = false, updatable = false, length = 50)
    @NonNull String name;

    @Column(name = "description", nullable = false, updatable = false, length = 250)
    @NonNull String description;

    @Column(name = "price", nullable = false)
    @NonNull Integer price;

    @Column(name = "rating")
    @NonNull Double rating;

    @Column(name = "created_at", nullable = false, updatable = false)
    @NonNull Instant createdAt;
}
