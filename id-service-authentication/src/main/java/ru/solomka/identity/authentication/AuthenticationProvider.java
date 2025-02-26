package ru.solomka.identity.authentication;

import ru.solomka.identity.principal.PrincipalEntity;

public interface AuthenticationProvider {
    PrincipalEntity authenticate(String username, String password);
}