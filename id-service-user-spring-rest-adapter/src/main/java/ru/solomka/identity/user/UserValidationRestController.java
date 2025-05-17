package ru.solomka.identity.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.query.GetEntityByIdQuery;
import ru.solomka.identity.common.exception.EntityNotFoundException;
import ru.solomka.identity.user.request.UserValidationRequest;

@RestController
@RequestMapping("/identity/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserValidationRestController {

    @NonNull CommandHandler<GetEntityByIdQuery, UserEntity> getEntityByIdQueryCommandHandler;

    @Operation(
            summary = "Validate user entity",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns 200 (OK) (User exists and valid)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404", description = "Exception: User not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(value = "/validate", produces = "application/json")
    public ResponseEntity<?> existsUser(@ParameterObject @RequestBody UserValidationRequest userValidationRequest) {
        try {
            getEntityByIdQueryCommandHandler.handle(new GetEntityByIdQuery(userValidationRequest.getUserId()));
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}