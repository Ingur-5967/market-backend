package ru.solomka.identity.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenPair {

    @NonNull TokenEntity accessToken;

    @NonNull TokenEntity refreshToken;
}
