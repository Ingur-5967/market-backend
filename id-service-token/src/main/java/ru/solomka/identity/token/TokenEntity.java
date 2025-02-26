package ru.solomka.identity.token;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class TokenEntity {

    @NonNull UUID id;

    @NonNull UUID userId;

    @NonNull Instant expiredAt;

    @NonNull String token;

    @NonNull TokenType tokenType;
}