package ru.solomka.identity.authentication.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationResponse {

    @NonNull UUID id;

    @NonNull String username;

    @NonNull String email;

    @NonNull Instant createdAt;
}