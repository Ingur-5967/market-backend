package ru.solomka.identity.user.mapper;

import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.user.User;
import ru.solomka.identity.user.UserEntity;

public class UserEntityUserMapper implements Mapper<UserEntity, User> {

    @Override
    public UserEntity mapToDomain(User infrastructureEntity) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public User mapToInfrastructure(UserEntity domainEntity) {
        return User.builder()
                .id(domainEntity.getId())
                .username(domainEntity.getUsername())
                .email(domainEntity.getEmail())
                .build();
    }
}