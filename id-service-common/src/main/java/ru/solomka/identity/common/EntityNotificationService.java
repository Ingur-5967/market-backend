package ru.solomka.identity.common;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityNotificationService<T extends Entity> {

    @NonNull EntityNotification<T, PrincipalEntity> notification;

    @NonNull PrincipalService principalService;

    public void notifyCreated(@NotNull T entity) {
        notification.notifyCreate(entity, principalService.getPrincipal());
    }

    public void notifyUpdated(@NotNull T entity) {
        notification.notifyUpdate(entity, principalService.getPrincipal());
    }

    public void notifyDeleted(@NotNull T entity) {
        notification.notifyDelete(entity, principalService.getPrincipal());
    }
}