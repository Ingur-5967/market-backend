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

@RestController
@RequestMapping("/identity/tokens")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessTokenValidateRestController {

    @NonNull CommandHandler<ExtractAndValidateAccessTokenCommand, TokenEntity> extractAndValidateAccessTokenCommandHandler;

    @PostMapping(value = "/validate", produces = "application/json")
    public ResponseEntity<TokenEntity> validateToken(@RequestBody String token) {
        TokenEntity tokenEntity = extractAndValidateAccessTokenCommandHandler.handle(new ExtractAndValidateAccessTokenCommand(token));
        return ResponseEntity.ok(tokenEntity);
    }
}