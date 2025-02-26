package ru.solomka.identity.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.Entity;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class UserEntity implements Entity {

    @NonNull UUID id;

    @NonNull String username;

    @NonNull String email;

    @NonNull String passwordHash;

    @NonNull Instant createdAt;
}