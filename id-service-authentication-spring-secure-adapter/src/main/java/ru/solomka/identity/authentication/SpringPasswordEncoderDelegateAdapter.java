package ru.solomka.identity.authentication;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpringPasswordEncoderDelegateAdapter implements EncoderDelegate {

    @NotNull PasswordEncoder passwordEncoder;

    @Override
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }

    @Override
    public boolean matches(@NotNull String value, @NotNull String encryptedValue) {
        return passwordEncoder.matches(value, encryptedValue);
    }
}