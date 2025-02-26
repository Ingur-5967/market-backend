package ru.solomka.identity.user;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JpaUserEntity {

    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "username", nullable = false)
    @NonNull String username;

    @Column(name = "password_hash", nullable = false)
    @NonNull String passwordHash;

    @Column(name = "email", nullable = false)
    @NonNull String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    @NonNull Instant createdAt;
}