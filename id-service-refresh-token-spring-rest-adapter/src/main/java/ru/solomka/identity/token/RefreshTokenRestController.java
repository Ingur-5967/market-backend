package ru.solomka.identity.token;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.token.request.RefreshAccessTokenRequest;
import ru.solomka.identity.token.response.RefreshAccessTokenResponse;

@RestController
@RequestMapping("/identity/tokens")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenRestController {

    @NonNull CommandHandler<IssueAccessTokenPairCommand, TokenPair> refreshTokenCommandHandler;

    @Operation(
            summary = "Refresh access token",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns updated token pair (Access and Refresh tokens)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshAccessTokenResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403", description = "Exception: Token is not valid (Expired, Structure and etc.)",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<RefreshAccessTokenResponse> refreshAccessToken(@ParameterObject @RequestBody RefreshAccessTokenRequest refreshAccessTokenRequest) {
        TokenPair pair = refreshTokenCommandHandler.handle(new IssueAccessTokenPairCommand(refreshAccessTokenRequest.getRefreshToken()));
        return ResponseEntity.ok(new RefreshAccessTokenResponse(
                pair.getAccessToken().getToken(),
                pair.getRefreshToken().getToken()
        ));
    }
}