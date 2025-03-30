package ru.solomka.identity.token;

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
import ru.solomka.identity.token.request.AccessTokenValidateRequest;

@RestController
@RequestMapping("/identity/tokens")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessTokenValidateRestController {

    @NonNull CommandHandler<ExtractAndValidateAccessTokenCommand, TokenEntity> extractAndValidateAccessTokenCommandHandler;

    @PostMapping(value = "/validate", produces = "application/json")
    public ResponseEntity<Boolean> validateToken(@RequestBody AccessTokenValidateRequest token) {
        TokenEntity tokenEntity = extractAndValidateAccessTokenCommandHandler.handle(new ExtractAndValidateAccessTokenCommand(token.getToken()));
        return ResponseEntity.ok(tokenEntity != null);
    }
}