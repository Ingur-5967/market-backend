package ru.solomka.identity.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
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
@RequiredArgsConstructor
public class UserRestController {

    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> getEntityByIdQueryCommandHandler;
    @NonNull CommandHandler<Object, UserEntity> getCurrentSessionUserEntityQueryHandler;

    @NonNull Mapper<UserEntity, User> mapper;


    @Operation(
            summary = "Get user entity by id",
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
    @GetMapping(produces = "application/json")
    public ResponseEntity<User> getUserById(
                                            @Parameter(
                                                    name = "Field 'userId'",
                                                    description = "The user UUID",
                                                    required = true,
                                                    examples = {
                                                            @ExampleObject(value = "f8a19d45-5784-4792-8678-64cb7fc0ece1"),
                                                    }
                                            )
                                            @RequestParam("userId") UUID userId) {

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
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}

            ),
            @ApiResponse(
                    responseCode = "401", description = "Exception: Unauthorized (Access token not found)",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<User> getCurrentUser() {
        UserEntity userEntity = getCurrentSessionUserEntityQueryHandler.handle(null);
        return ResponseEntity.ok(mapper.mapToInfrastructure(userEntity));
    }
}