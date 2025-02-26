package ru.solomka.product.principal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.product.principal.exception.PrincipalException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrincipalService {

    @NotNull PrincipalRepository principalRepository;

    public @NotNull PrincipalEntity getPrincipal() throws PrincipalException {
        return principalRepository.findPrincipal().orElseThrow(() -> new PrincipalException("Principal not found!"));
    }

    public boolean isAuthenticated() {
        return principalRepository.isAuthenticated();
    }

    public @NotNull PrincipalEntity setPrincipal(@NotNull PrincipalEntity principal) {
        return principalRepository.setPrincipal(principal);
    }
}