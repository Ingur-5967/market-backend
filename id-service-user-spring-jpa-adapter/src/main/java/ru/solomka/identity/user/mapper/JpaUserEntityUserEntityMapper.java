package ru.solomka.identity.user.mapper;

import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.user.JpaUserEntity;
import ru.solomka.identity.user.UserEntity;

public class JpaUserEntityUserEntityMapper implements Mapper<UserEntity, JpaUserEntity> {

    @Override
    public UserEntity mapToDomain(JpaUserEntity infrastructureEntity) {
        return UserEntity.builder()
                .id(infrastructureEntity.getId())
                .username(infrastructureEntity.getUsername())
                .passwordHash(infrastructureEntity.getPasswordHash())
                .email(infrastructureEntity.getEmail())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }

    @Override
    public JpaUserEntity mapToInfrastructure(UserEntity domainEntity) {
        return JpaUserEntity.builder()
                .id(domainEntity.getId())
                .username(domainEntity.getUsername())
                .passwordHash(domainEntity.getPasswordHash())
                .email(domainEntity.getEmail())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }
}
