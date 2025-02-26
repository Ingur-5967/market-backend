package ru.solomka.identity.token;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.Entity;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class RefreshTokenEntity implements Entity {

    @NonNull UUID id;

    @NonNull Instant createdAt;
}