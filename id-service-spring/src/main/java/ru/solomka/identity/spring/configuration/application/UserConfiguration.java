package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.EntityRepository;
import ru.solomka.identity.common.cqrs.query.handler.GetEntityByIdQueryHandler;
import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.kafka.KafkaEntityNotificationAdapter;
import ru.solomka.identity.kafka.event.UserCreatedEvent;
import ru.solomka.identity.kafka.event.UserDeletedEvent;
import ru.solomka.identity.kafka.event.UserUpdatedEvent;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.*;
import ru.solomka.identity.user.mapper.JpaUserEntityUserEntityMapper;
import ru.solomka.identity.user.mapper.UserEntityUserMapper;

@Configuration
@EnableJpaRepositories(basePackageClasses = CrudUserRepository.class)
@EntityScan(basePackageClasses = JpaUserEntity.class)
public class UserConfiguration {

    @Bean
    UserService userService(@NonNull UserRepository userRepository,
                            EntityNotificationService<UserEntity> entityEntityNotificationService) {
        return new UserService(userRepository, entityEntityNotificationService);
    }

    @Bean
    UserRepository userRepository(@NonNull Mapper<UserEntity, JpaUserEntity> mapper,
                                  @NonNull CrudUserRepository crudUserRepository) {
        return new JpaUserEntityRepositoryAdapter(crudUserRepository, mapper);
    }

    @Bean
    JpaUserEntityUserEntityMapper jpaUserEntityUserEntityMapper() {
        return new JpaUserEntityUserEntityMapper();
    }

    @Bean
    EntityNotification<UserEntity, PrincipalEntity> userNotificationRepository(@NonNull KafkaTemplate<String, UserCreatedEvent> createNotification,
                                                                               @NonNull KafkaTemplate<String, UserUpdatedEvent> updateNotification,
                                                                               @NonNull KafkaTemplate<String, UserDeletedEvent> deleteNotification) {
        return new KafkaEntityNotificationAdapter(createNotification, updateNotification, deleteNotification);
    }

    @Bean
    EntityNotificationService<UserEntity> entityNotificationService(@NonNull EntityNotification<UserEntity, PrincipalEntity> entityPrincipalEntityEntityNotification,
                                                                    @NonNull PrincipalService principalService) {
        return new EntityNotificationService<>(entityPrincipalEntityEntityNotification, principalService);
    }

    @Bean
    UserEntityUserMapper userEntityUserMapper() {
        return new UserEntityUserMapper();
    }

    @Bean
    GetEntityByIdQueryHandler<UserEntity> getUserByIdQueryHandler(@NonNull UserService userService) {
        return new GetEntityByIdQueryHandler<>(userService);
    }
}