package ru.solomka.identity.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CrudUserRepository extends CrudRepository<JpaUserEntity, UUID> {
    Optional<JpaUserEntity> findByUsername(String username);
    Optional<JpaUserEntity> findByEmail(String email);
}