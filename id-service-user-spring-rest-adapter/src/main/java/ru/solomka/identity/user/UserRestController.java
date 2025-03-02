package ru.solomka.identity.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.query.GetEntityByIdQuery;
import ru.solomka.identity.common.http.RestResponse;
import ru.solomka.identity.common.mapper.Mapper;

import java.util.UUID;

@RestController
@RequestMapping("/identity/users")
@RequiredArgsConstructor
public class UserRestController {

    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> getEntityByIdQueryCommandHandler;
    @NonNull Mapper<UserEntity, User> mapper;

    @GetMapping(value = "/{userId}", produces = "application/json")
    public RestResponse<User> getUserById(@PathVariable("userId") UUID userId) {
        GetEntityByIdQuery getUserByIdQuery = new GetEntityByIdQuery(userId);
        UserEntity userEntity = getEntityByIdQueryCommandHandler.handle(getUserByIdQuery);
        return RestResponse.from(mapper.mapToInfrastructure(userEntity));
    }
}