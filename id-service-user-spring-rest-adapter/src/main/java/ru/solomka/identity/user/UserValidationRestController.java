package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.query.GetEntityByIdQuery;

import java.util.UUID;

@RestController
@RequestMapping("/identity/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserValidationRestController {

    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> getEntityByIdQueryCommandHandler;

    @PostMapping(value = "/validate/{userId}", produces = "application/json")
    public ResponseEntity<Boolean> existsUser(@PathVariable("userId") UUID userId) {
        GetEntityByIdQuery getUserByIdQuery = new GetEntityByIdQuery(userId);
        UserEntity userEntity = getEntityByIdQueryCommandHandler.handle(getUserByIdQuery);
        return ResponseEntity.ok(userEntity != null);
    }
}