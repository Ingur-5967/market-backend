package ru.solomka.identity.user.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.user.UserEntity;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserUpdatedEvent implements KafkaEvent {

    @NonNull PrincipalEntity principal;

    @NonNull UserEntity userEntity;
}
