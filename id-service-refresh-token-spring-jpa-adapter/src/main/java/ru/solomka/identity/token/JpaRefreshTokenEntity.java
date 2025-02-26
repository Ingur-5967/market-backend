package ru.solomka.identity.token;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JpaRefreshTokenEntity {
    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @NonNull Instant createdAt;
}