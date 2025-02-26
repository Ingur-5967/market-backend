package ru.solomka.identity.authentication;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.authentication.exception.AuthenticationException;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    @NonNull UserService userService;

    @NonNull EncoderDelegate encoderDelegate;

    @Override
    public PrincipalEntity authenticate(String username, String password) {

        UserEntity searchUserEntityByUsername = userService.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(username));

        if(!encoderDelegate.matches(password, searchUserEntityByUsername.getPasswordHash()))
            throw new AuthenticationException("Bad credentials for 'password''");

        return new PrincipalEntity(searchUserEntityByUsername.getId(), username);
    }
}
