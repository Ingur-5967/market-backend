package ru.solomka.identity.spring.configuration.application;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.solomka.identity.common.Entity;
import ru.solomka.identity.common.EntityNotificationService;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.user.KafkaEntityNotificationAdapter;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.event.UserCreatedEvent;
import ru.solomka.identity.user.event.UserDeletedEvent;
import ru.solomka.identity.user.event.UserUpdatedEvent;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaTemplate<String, UserCreatedEvent> userCreatedEventKafkaTemplate(@NotNull ProducerFactory<String, UserCreatedEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, UserUpdatedEvent> userUpdatedEventKafkaTemplate(@NotNull ProducerFactory<String, UserUpdatedEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, UserDeletedEvent> userDeletedEventKafkaTemplate(@NotNull ProducerFactory<String, UserDeletedEvent> factory) {

        return new KafkaTemplate<>(factory);
    }

    @Bean
    EntityNotificationService<UserEntity> entityNotificationService(@NotNull EntityNotification<UserEntity, PrincipalEntity> entityNotificationService,
                                                                    @NotNull PrincipalService principalService) {
        return new EntityNotificationService<>(entityNotificationService, principalService);
    }

    @Bean
    KafkaEntityNotificationAdapter entityPrincipalEntityEntityNotification(@NotNull KafkaTemplate<String, UserCreatedEvent> userCreatedEventKafkaTemplate,
                                                                                        @NotNull KafkaTemplate<String, UserUpdatedEvent> userUpdatedEventKafkaTemplate,
                                                                                        @NotNull KafkaTemplate<String, UserDeletedEvent> userDeletedEventKafkaTemplate) {
        return new KafkaEntityNotificationAdapter(userCreatedEventKafkaTemplate, userUpdatedEventKafkaTemplate, userDeletedEventKafkaTemplate);
    }
}