package ru.solomka.product.spring.configuration.security.authenticate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnetimeJwtAuthenticationToken extends AbstractAuthenticationToken {

    @NonNull String token;

    public OnetimeJwtAuthenticationToken(@NotNull String token) {
        super(List.of());
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }
}