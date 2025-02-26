package ru.solomka.identity.token;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CrudRefreshTokenRepository extends CrudRepository<JpaRefreshTokenEntity, UUID> {
}