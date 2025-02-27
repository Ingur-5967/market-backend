package ru.solomka.identity.token;

import org.springframework.data.repository.CrudRepository;
import ru.solomka.identity.common.BaseCrudRepository;

import java.util.UUID;

public interface JpaRefreshTokenRepository extends BaseCrudRepository<JpaRefreshTokenEntity> {
}