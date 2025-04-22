package ru.solomka.identity.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.query.GetEntityByIdQuery;
import ru.solomka.identity.common.mapper.Mapper;
import ru.solomka.identity.user.entity.User;

import java.util.UUID;

@RestController
@RequestMapping("/identity/users")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class UserRestController {

    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> getEntityByIdQueryCommandHandler;
    @NonNull CommandHandler<Void, UserEntity> getCurrentSessionUserEntityQueryHandler;

    @NonNull Mapper<UserEntity, User> mapper;


    @Operation(
            summary = "Get user entity by uid",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns user entity",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", description = "Exception: Entity not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<User> getUserById(
                                            @Parameter(
                                                    name = "Field 'userId'",
                                                    description = "The user UUID",
                                                    required = true,
                                                    examples = {
                                                            @ExampleObject(value = "f8a19d45-5784-4792-8678-64cb7fc0ece1"),
                                                    }
                                            )
                                            @PathVariable("userId") UUID userId) {

        GetEntityByIdQuery getUserByIdQuery = new GetEntityByIdQuery(userId);
        UserEntity userEntity = getEntityByIdQueryCommandHandler.handle(getUserByIdQuery);
        return ResponseEntity.ok(mapper.mapToInfrastructure(userEntity));
    }

    @SneakyThrows
    @Operation(
            summary = "Get current session user entity",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns current session user entity",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401", description = "Exception: Unauthorized",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<User> getCurrentUser() {
        UserEntity userEntity = getCurrentSessionUserEntityQueryHandler.handle(Void.TYPE.getDeclaredConstructor().newInstance());
        return ResponseEntity.ok(mapper.mapToInfrastructure(userEntity));
    }
}