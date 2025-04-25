package ru.solomka.identity.token;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.token.exception.TokenException;
import ru.solomka.identity.token.request.AccessTokenValidateRequest;

@RestController
@RequestMapping("/identity/tokens")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessTokenValidateRestController {

    @NonNull CommandHandler<ExtractAndValidateAccessTokenCommand, TokenEntity> extractAndValidateAccessTokenCommandHandler;

    @Operation(
            summary = "Validate access-token",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns 'True' (Token is valid)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400", description = "Exception: Token is not valid (Expired, Structure and etc.)",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(value = "/validate", produces = "application/json")
    public ResponseEntity<?> validateToken(@RequestBody AccessTokenValidateRequest token) {
        try {
            extractAndValidateAccessTokenCommandHandler.handle(new ExtractAndValidateAccessTokenCommand(token.getToken()));
            return ResponseEntity.ok().build();
        } catch (TokenException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}