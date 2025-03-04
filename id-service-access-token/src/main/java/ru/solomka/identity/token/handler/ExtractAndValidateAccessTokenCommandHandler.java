package ru.solomka.identity.token.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.token.ExtractAndValidateAccessTokenCommand;
import ru.solomka.identity.token.TokenEntity;
import ru.solomka.identity.token.TokenExtractor;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExtractAndValidateAccessTokenCommandHandler implements CommandHandler<ExtractAndValidateAccessTokenCommand, TokenEntity> {

    @NonNull TokenExtractor tokenExtractor;

    @Override
    public TokenEntity handle(ExtractAndValidateAccessTokenCommand commandEntity) {
        return tokenExtractor.extract(commandEntity.getToken());
    }
}