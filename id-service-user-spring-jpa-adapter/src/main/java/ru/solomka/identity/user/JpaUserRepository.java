package ru.solomka.identity.user;

import org.springframework.data.repository.CrudRepository;
import ru.solomka.identity.common.BaseCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends BaseCrudRepository<JpaUserEntity> {
    Optional<JpaUserEntity> findByUsername(String username);
    Optional<JpaUserEntity> findByEmail(String email);
}