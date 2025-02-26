package ru.solomka.identity.authentication.cqrs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterUserCommand {

    @NonNull String username;

    @NonNull String password;

    @NonNull String email;
}
