package ru.solomka.identity.authentication;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.authentication.exception.AuthenticationException;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService implements AuthenticationProvider {

    @NotNull EncoderDelegate passwordEncoderDelegate;

    @NotNull UserService userService;

    @NotNull PrincipalService principalService;

    @Override
    public @NotNull PrincipalEntity authenticate(@NotNull String login, @NotNull String password) throws AuthenticationException {
        UserEntity user = userService.findByUsername(login).orElseThrow(() -> new EntityNotFoundException("User not found with login: %s".formatted(login)));
        if (!passwordEncoderDelegate.matches(password, user.getPasswordHash())) {
            throw new AuthenticationException("Incorrect password!");
        }
        return principalService.setPrincipal(PrincipalEntity.builder().id(user.getId()).username(user.getUsername()).build());
    }
}