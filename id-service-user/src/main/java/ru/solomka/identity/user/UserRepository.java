package ru.solomka.identity.user;

import ru.solomka.identity.common.EntityRepository;

import java.util.Optional;

public interface UserRepository extends EntityRepository<UserEntity> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}