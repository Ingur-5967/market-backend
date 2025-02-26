package ru.solomka.identity.token.mapper;

import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.token.JpaRefreshTokenEntity;
import ru.solomka.identity.token.RefreshTokenEntity;

public class JpaRefreshTokenEntityRefreshTokenEntityMapper implements Mapper<RefreshTokenEntity, JpaRefreshTokenEntity> {

    @Override
    public RefreshTokenEntity mapToDomain(JpaRefreshTokenEntity infrastructureEntity) {
        return RefreshTokenEntity.builder().id(infrastructureEntity.getId()).build();
    }

    @Override
    public JpaRefreshTokenEntity mapToInfrastructure(RefreshTokenEntity domainEntity) {
        return JpaRefreshTokenEntity.builder().id(domainEntity.getId()).build();
    }
}