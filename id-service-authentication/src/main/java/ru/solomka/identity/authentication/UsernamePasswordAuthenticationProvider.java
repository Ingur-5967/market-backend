package ru.solomka.identity.authentication;

import lombok.NonNull;
import ru.solomka.identity.user.UserService;

public class UsernamePasswordAuthenticationProvider extends AbstractAuthenticationProvider {
    public UsernamePasswordAuthenticationProvider(@NonNull UserService userService, @NonNull EncoderDelegate encoderDelegate) {
        super(userService, encoderDelegate);
    }
}