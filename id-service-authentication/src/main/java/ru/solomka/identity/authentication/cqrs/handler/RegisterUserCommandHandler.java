package ru.solomka.identity.authentication.cqrs.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.authentication.EncoderDelegate;
import ru.solomka.identity.authentication.cqrs.RegisterUserCommand;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.exception.EntityAlreadyExistsException;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.token.*;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand, TokenPair> {

    @NotNull Duration accessTokenLifetime;

    @NotNull Duration refreshTokenLifetime;

    @NotNull PrincipalService principalService;

    @NotNull RefreshTokenService refreshTokenService;

    @NotNull TokenPairFactory tokenPairFactory;

    @NotNull UserService userService;

    @NotNull EncoderDelegate passwordEncoderDelegate;

    @Override
    public @NotNull TokenPair handle(@NotNull RegisterUserCommand registerUserCommand) {
        String encodedPassword = passwordEncoderDelegate.encode(registerUserCommand.getPassword());

        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username(registerUserCommand.getUsername())
                .email(registerUserCommand.getEmail())
                .passwordHash(encodedPassword)
                .build();

        if(userService.findByUsername(userEntity.getUsername()).isPresent() || userService.findByEmail(userEntity.getEmail()).isPresent())
            throw new EntityAlreadyExistsException("User with identity credits [username/email] already exists");

        PrincipalEntity principal = new PrincipalEntity(
                userEntity.getId(),
                userEntity.getUsername()
        );

        principalService.setPrincipal(principal);

        userService.create(userEntity);

        TokenPair tokenPair = tokenPairFactory.create(
                principal,
                accessTokenLifetime,
                refreshTokenLifetime
        );

        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().id(tokenPair.getRefreshToken().getId()).build();
        refreshTokenService.create(refreshTokenEntity);

        return tokenPair;
    }
}