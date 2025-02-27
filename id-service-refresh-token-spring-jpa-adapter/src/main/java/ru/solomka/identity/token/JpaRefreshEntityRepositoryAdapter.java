package ru.solomka.identity.token;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.BaseCrudRepository;
import ru.solomka.identity.common.BaseJpaEntityRepositoryAdapter;
import ru.solomka.identity.common.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaRefreshEntityRepositoryAdapter extends BaseJpaEntityRepositoryAdapter<JpaRefreshTokenEntity, RefreshTokenEntity> implements RefreshTokenRepository {
    public JpaRefreshEntityRepositoryAdapter(@NonNull JpaRefreshTokenRepository jpaRefreshTokenRepository,
                                             @NonNull Mapper<RefreshTokenEntity, JpaRefreshTokenEntity> mapper) {
        super(jpaRefreshTokenRepository, mapper);
    }
}