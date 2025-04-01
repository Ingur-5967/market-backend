package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.identity.common.cqrs.query.handler.GetEntityByIdQueryHandler;
import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.*;
import ru.solomka.identity.user.JpaUserEntityUserEntityMapper;
import ru.solomka.identity.user.cqrs.GetCurrentUserSessionQueryHandler;
import ru.solomka.identity.user.mapper.UserEntityUserMapper;

@Configuration
@EnableJpaRepositories(basePackageClasses = JpaUserRepository.class)
@EntityScan(basePackageClasses = JpaUserEntity.class)
public class UserConfiguration {

    @Bean
    UserService userService(@NonNull UserRepository userRepository,
                            @NonNull PrincipalService principalService) {
        return new UserService(userRepository, principalService);
    }

    @Bean
    UserRepository userRepository(@NonNull Mapper<UserEntity, JpaUserEntity> mapper,
                                  @NonNull JpaUserRepository crudUserRepository) {
        return new JpaUserEntityRepositoryAdapter(crudUserRepository, mapper);
    }

    @Bean
    JpaUserEntityUserEntityMapper jpaUserEntityUserEntityMapper() {
        return new JpaUserEntityUserEntityMapper();
    }


    @Bean
    UserEntityUserMapper userEntityUserMapper() {
        return new UserEntityUserMapper();
    }

    @Bean
    GetEntityByIdQueryHandler<UserEntity> getUserByIdQueryHandler(@NonNull UserService userService) {
        return new GetEntityByIdQueryHandler<>(userService);
    }

    @Bean
    GetCurrentUserSessionQueryHandler getCurrentUserSessionQueryHandler(@NonNull PrincipalService principalService,
                                                                        @NonNull GetEntityByIdQueryHandler<UserEntity> getEntityByIdQueryHandler) {
        return new GetCurrentUserSessionQueryHandler(principalService, getEntityByIdQueryHandler);
    }
}