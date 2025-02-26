package ru.solomka.identity.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@ToString
public class User {

    @NonNull UUID id;

    @NonNull String username;

    @NonNull String email;
}