package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import ru.solomka.identity.common.EntityNotification;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.user.event.UserCreatedEvent;
import ru.solomka.identity.user.event.UserDeletedEvent;
import ru.solomka.identity.user.event.UserUpdatedEvent;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaEntityNotificationAdapter implements EntityNotification<UserEntity, PrincipalEntity> {

    @NonNull KafkaTemplate<String, UserCreatedEvent> createNotification;

    @NonNull KafkaTemplate<String, UserUpdatedEvent> updateNotification;

    @NonNull KafkaTemplate<String, UserDeletedEvent> deleteNotification;

    @Override
    public void notifyCreate(UserEntity user, PrincipalEntity principal) {
        createNotification.send(KafkaTopicPoints.ID_SERVICE_CREATE_EVENT_TOPIC, new UserCreatedEvent(principal, user));
    }

    @Override
    public void notifyUpdate(UserEntity user, PrincipalEntity principal) {
        updateNotification.send(KafkaTopicPoints.ID_SERVICE_UPDATE_EVENT_TOPIC, new UserUpdatedEvent(principal, user));
    }

    @Override
    public void notifyDelete(UserEntity user, PrincipalEntity principal) {
        deleteNotification.send(KafkaTopicPoints.ID_SERVICE_DELETE_EVENT_TOPIC, new UserDeletedEvent(principal, user));
    }
}