package ru.solomka.identity.principal;

import java.util.Optional;

public interface PrincipalRepository {

    Optional<PrincipalEntity> findPrincipal();

    PrincipalEntity setPrincipal(PrincipalEntity principal);

    boolean isAuthenticated();
}