package ru.solomka.identity.token;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JpaRefreshEntityRepositoryAdapter implements RefreshTokenRepository {

    @NonNull CrudRefreshTokenRepository refreshTokenRepository;

    @NonNull Mapper<RefreshTokenEntity, JpaRefreshTokenEntity> mapper;

    @Override
    public RefreshTokenEntity create(RefreshTokenEntity entity) {
        JpaRefreshTokenEntity createRefreshTokenEntity = mapper.mapToInfrastructure(entity);
        createRefreshTokenEntity = refreshTokenRepository.save(createRefreshTokenEntity);
        return mapper.mapToDomain(createRefreshTokenEntity);
    }

    @Override
    public RefreshTokenEntity update(RefreshTokenEntity entity) {
        JpaRefreshTokenEntity updateRefreshTokenEntity = mapper.mapToInfrastructure(entity);
        updateRefreshTokenEntity = refreshTokenRepository.save(updateRefreshTokenEntity);
        return mapper.mapToDomain(updateRefreshTokenEntity);
    }

    @Override
    public RefreshTokenEntity deleteById(UUID id) {
        JpaRefreshTokenEntity deleteRefreshTokenEntity = refreshTokenRepository.findById(id).orElseThrow(RuntimeException::new);
        refreshTokenRepository.delete(deleteRefreshTokenEntity);
        return mapper.mapToDomain(deleteRefreshTokenEntity);
    }

    @Override
    public Optional<RefreshTokenEntity> findById(UUID id) {
        return refreshTokenRepository.findById(id).map(mapper::mapToDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return refreshTokenRepository.existsById(id);
    }
}
