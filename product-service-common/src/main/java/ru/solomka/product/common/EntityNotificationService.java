package ru.solomka.product.common;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.product.principal.PrincipalEntity;
import ru.solomka.product.principal.PrincipalService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityNotificationService<T extends Entity> {

    @NonNull EntityNotification<T, PrincipalEntity> notification;

    @NonNull
    PrincipalService principalService;

    public void receiveNotifyCreated(@NotNull T entity) {
        notification.receiveNotifyCreated(entity, principalService.getPrincipal());
    }

    public void receiveNotifyUpdated(@NotNull T entity) {
        notification.receiveNotifyUpdated(entity, principalService.getPrincipal());
    }

    public void receiveNotifyDeleted(@NotNull T entity) {
        notification.receiveNotifyDeleted(entity, principalService.getPrincipal());
    }
}