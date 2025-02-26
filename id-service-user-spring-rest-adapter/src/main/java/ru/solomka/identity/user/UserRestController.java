package ru.solomka.identity.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.query.GetEntityByIdQuery;
import ru.solomka.identity.common.mapper.Mapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> commandHandler;

    @NonNull Mapper<UserEntity, User> mapper;

    @GetMapping(value = "/{userId}", produces = "application/json")
    public EntityResponse<User> getUserById(@PathVariable("userId") UUID userId) {
        GetEntityByIdQuery getUserByIdQuery = new GetEntityByIdQuery(userId);
        UserEntity userEntity = commandHandler.handle(getUserByIdQuery);
        return EntityResponse.fromObject(mapper.mapToInfrastructure(userEntity)).build();
    }
}