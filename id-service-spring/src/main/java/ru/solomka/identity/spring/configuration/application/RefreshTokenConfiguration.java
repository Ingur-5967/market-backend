package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.identity.common.EntityRepository;
import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.token.*;
import ru.solomka.identity.token.JpaRefreshTokenEntityRefreshTokenEntityMapper;

@Configuration
@EnableJpaRepositories(basePackageClasses = JpaRefreshTokenRepository.class)
@EntityScan(basePackageClasses = JpaRefreshTokenEntity.class)
public class RefreshTokenConfiguration {

    @Bean
    RefreshTokenRepository refreshTokenRepository(@NonNull JpaRefreshTokenRepository crudRefreshTokenRepository,
                                                  @NonNull Mapper<RefreshTokenEntity, JpaRefreshTokenEntity> refreshTokenEntityJpaRefreshTokenEntityMapper) {
        return new JpaRefreshEntityRepositoryAdapter(
                crudRefreshTokenRepository, refreshTokenEntityJpaRefreshTokenEntityMapper

        );
    }

    @Bean
    RefreshTokenService refreshTokenService(@NonNull EntityRepository<RefreshTokenEntity> userEntityRepository) {
        return new RefreshTokenService(userEntityRepository);
    }

    @Bean
    JpaRefreshTokenEntityRefreshTokenEntityMapper jpaRefreshTokenEntityRefreshTokenEntityMapper() {
        return new JpaRefreshTokenEntityRefreshTokenEntityMapper();
    }
}