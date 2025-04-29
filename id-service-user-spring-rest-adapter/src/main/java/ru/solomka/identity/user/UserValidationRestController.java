package ru.solomka.identity.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.query.GetEntityByIdQuery;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.user.request.UserValidationRequest;

import java.util.UUID;

@RestController
@RequestMapping("/identity/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserValidationRestController {

    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> getEntityByIdQueryCommandHandler;

    @PostMapping(value = "/validate", produces = "application/json")
    public ResponseEntity<?> existsUser(@RequestBody UserValidationRequest userValidationRequest) {
        try {
            getEntityByIdQueryCommandHandler.handle(new GetEntityByIdQuery(userValidationRequest.getUserId()));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}