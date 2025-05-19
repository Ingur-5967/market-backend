package ru.solomka.identity.user.event;

import lombok.NonNull;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.user.UserEntity;

public interface KafkaEvent {

    @NonNull PrincipalEntity getPrincipal();

    @NonNull
    UserEntity getUserEntity();
}