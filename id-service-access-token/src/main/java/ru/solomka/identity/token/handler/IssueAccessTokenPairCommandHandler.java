package ru.solomka.identity.token.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.token.*;
import ru.solomka.identity.token.exception.TokenExpiredException;
import ru.solomka.identity.token.exception.TokenVerificationException;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IssueAccessTokenPairCommandHandler implements CommandHandler<IssueAccessTokenPairCommand, TokenPair> {

    @NonNull UserService userService;

    @NonNull PrincipalService principalService;

    @NonNull TokenFactory tokenFactory;

    @NonNull TokenExtractor tokenExtractor;

    @NonNull RefreshTokenService refreshTokenService;

    @NotNull Duration accessTokenLifetime;

    @NotNull Duration refreshTokenLifetime;

    @Override
    public TokenPair handle(IssueAccessTokenPairCommand commandEntity) {
        if(commandEntity.getRefreshToken().isEmpty())
            throw new IllegalArgumentException("Refresh token cannot is empty for this action");

        TokenEntity entity = tokenExtractor.extract(commandEntity.getRefreshToken());

        if(Instant.now().isAfter(entity.getExpiredAt()))
            throw new TokenExpiredException("Refresh token expired");

        if(!refreshTokenService.existsById(entity.getId()))
            throw new TokenVerificationException("Refresh token verification failed");

        UserEntity userEntity = userService.getById(entity.getUserId());

        principalService.setPrincipal(PrincipalEntity.builder().id(entity.getUserId()).username(userEntity.getUsername()).build());

        TokenEntity refreshToken = tokenFactory.create(
                principalService.getPrincipal(),
                Duration.ofMillis(refreshTokenLifetime.toMillis()),
                TokenType.REFRESH_TOKEN
        );

        TokenEntity accessToken = tokenFactory.create(
                principalService.getPrincipal(),
                Duration.ofMillis(accessTokenLifetime.toMillis()),
                TokenType.ACCESS_TOKEN
        );

        refreshTokenService.deleteById(entity.getId());

        refreshTokenService.create(RefreshTokenEntity.builder().id(refreshToken.getId()).build());

        return new TokenPair(accessToken, refreshToken);
    }
}