package ru.solomka.identity.principal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrincipalTokenAuthentication extends AbstractAuthenticationToken {

    @NonNull PrincipalEntity principal;

    public PrincipalTokenAuthentication(@NonNull PrincipalEntity principal) {
        super(Collections.emptyList());
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}