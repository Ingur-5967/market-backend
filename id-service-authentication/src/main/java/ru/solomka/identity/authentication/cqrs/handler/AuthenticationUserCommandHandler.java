package ru.solomka.identity.authentication.cqrs.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.authentication.AuthenticationService;
import ru.solomka.identity.authentication.cqrs.AuthenticationUserCommand;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.token.TokenPair;
import ru.solomka.identity.token.TokenPairFactory;

import java.time.Duration;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationUserCommandHandler implements CommandHandler<AuthenticationUserCommand, TokenPair> {

    @NotNull AuthenticationService authenticationService;

    @NotNull TokenPairFactory tokenPairFactory;

    @NotNull Duration accessTokenLifetime;

    @NotNull Duration refreshTokenLifetime;

    @Override
    public @NotNull TokenPair handle(@NotNull AuthenticationUserCommand authenticateUserCommand) {
        PrincipalEntity principal = authenticationService.authenticate(
                authenticateUserCommand.getUsername(),
                authenticateUserCommand.getPassword()
        );
        return tokenPairFactory.create(principal, accessTokenLifetime, refreshTokenLifetime);
    }
}