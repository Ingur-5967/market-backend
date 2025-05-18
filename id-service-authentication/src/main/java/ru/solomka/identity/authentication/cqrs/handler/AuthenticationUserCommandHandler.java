package ru.solomka.identity.authentication.cqrs.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.authentication.AuthenticationService;
import ru.solomka.identity.authentication.cqrs.AuthenticationUserCommand;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.token.*;

import java.time.Duration;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationUserCommandHandler implements CommandHandler<AuthenticationUserCommand, TokenPair> {

    @NotNull AuthenticationService authenticationService;

    @NotNull RefreshTokenService refreshTokenService;

    @NotNull TokenPairFactory tokenPairFactory;

    @NotNull Duration accessTokenLifetime;

    @NotNull Duration refreshTokenLifetime;

    @Override
    public @NotNull TokenPair handle(@NotNull AuthenticationUserCommand authenticateUserCommand) {
        PrincipalEntity principal = authenticationService.authenticate(
                authenticateUserCommand.getUsername(),
                authenticateUserCommand.getPassword()
        );

        TokenPair tokenPair = tokenPairFactory.create(principal, accessTokenLifetime, refreshTokenLifetime);

        if(refreshTokenService.existsById(tokenPair.getRefreshToken().getId()))
            refreshTokenService.deleteById(tokenPair.getRefreshToken().getId());

        refreshTokenService.create(RefreshTokenEntity.builder().id(tokenPair.getRefreshToken().getId()).build());

        return tokenPair;
    }
}