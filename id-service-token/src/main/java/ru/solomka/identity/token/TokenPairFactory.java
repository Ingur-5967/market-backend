package ru.solomka.identity.token;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.principal.PrincipalEntity;

import java.time.Duration;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenPairFactory {

    @NotNull TokenFactory refreshTokenFactory;

    @NotNull TokenFactory accessTokenFactory;

    public @NotNull TokenPair create(
            @NotNull PrincipalEntity principalEntity,
            @NotNull Duration accessTokenLifetime,
            @NotNull Duration refreshTokenLifetime
    ) {
        TokenEntity accessToken = accessTokenFactory.create(principalEntity, accessTokenLifetime, TokenType.ACCESS_TOKEN);
        TokenEntity refreshToken = refreshTokenFactory.create(principalEntity, refreshTokenLifetime, TokenType.REFRESH_TOKEN);
        return new TokenPair(accessToken, refreshToken);
    }
}