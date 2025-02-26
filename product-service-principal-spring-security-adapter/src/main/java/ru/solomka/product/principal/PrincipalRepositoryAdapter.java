package ru.solomka.product.principal;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class PrincipalRepositoryAdapter implements PrincipalRepository {

    @Override
    public Optional<PrincipalEntity> findPrincipal() {
        return Optional.of((PrincipalEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public PrincipalEntity setPrincipal(PrincipalEntity principal) {
        PrincipalTokenAuthentication authentication = new PrincipalTokenAuthentication(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }
}