package ru.solomka.identity.token;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.common.EntityRepository;
import ru.solomka.identity.common.EntityService;
import ru.solomka.identity.principal.PrincipalService;

import java.time.Instant;

public class RefreshTokenService extends EntityService<RefreshTokenEntity> {
    public RefreshTokenService(@NonNull EntityRepository<RefreshTokenEntity> repository,
                               @NotNull PrincipalService principalService) {
        super(repository, principalService);
    }

    @Override
    public @NotNull RefreshTokenEntity create(@NotNull RefreshTokenEntity entity) {
        entity.setCreatedAt(Instant.now());
        System.out.println(entity);
        return repository.create(entity);
    }
}