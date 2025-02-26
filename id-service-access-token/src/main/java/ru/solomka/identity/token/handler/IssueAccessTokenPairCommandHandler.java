package ru.solomka.identity.token.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.token.*;
import ru.solomka.identity.token.exception.TokenExpiredException;
import ru.solomka.identity.token.exception.TokenVerificationException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IssueAccessTokenPairCommandHandler implements CommandHandler<IssueAccessTokenPairCommand, TokenPair> {

    @NonNull PrincipalService principalService;

    @NonNull TokenFactory tokenFactory;

    @NonNull TokenExtractor tokenExtractor;

    @NonNull RefreshTokenService refreshTokenService;

    @Override
    public TokenPair handle(IssueAccessTokenPairCommand commandEntity) {
        if(commandEntity.getRefreshToken().isEmpty())
            throw new IllegalArgumentException("Refresh token cannot is empty for this action");

        TokenEntity entity = tokenExtractor.extract(commandEntity.getRefreshToken());

        if(entity.getExpiredAt().isAfter(Instant.now()))
            throw new TokenExpiredException("Refresh token expired");

        if(!refreshTokenService.existsById(entity.getId()))
            throw new TokenVerificationException("Refresh token verification failed");

        TokenEntity refreshToken = tokenFactory.create(
                principalService.getPrincipal(),
                Duration.of(1000, ChronoUnit.MINUTES),
                TokenType.REFRESH_TOKEN
        );

        TokenEntity accessToken = tokenFactory.create(
                principalService.getPrincipal(),
                Duration.of(1000, ChronoUnit.MINUTES),
                TokenType.ACCESS_TOKEN
        );

        refreshTokenService.deleteById(refreshToken.getId());

        refreshTokenService.create(RefreshTokenEntity.builder().id(refreshToken.getId()).build());

        return new TokenPair(refreshToken, accessToken);
    }
}