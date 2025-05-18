package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.identity.principal.PrincipalRepository;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.spring.configuration.properties.IdentityTokenProperties;
import ru.solomka.identity.token.RefreshTokenService;
import ru.solomka.identity.token.TokenExtractor;
import ru.solomka.identity.token.TokenFactory;
import ru.solomka.identity.token.handler.ExtractAndValidateAccessTokenCommandHandler;
import ru.solomka.identity.token.handler.IssueAccessTokenPairCommandHandler;
import ru.solomka.identity.user.UserService;

import java.time.Duration;

@Configuration
public class AccessTokenConfiguration {

    @Bean
    IssueAccessTokenPairCommandHandler issueAccessTokenPairCommandHandler(@NonNull UserService userService,
                                                                          @NonNull PrincipalService principalService,
                                                                          @NonNull TokenFactory tokenFactory,
                                                                          @NonNull TokenExtractor tokenExtractor,
                                                                          @NonNull RefreshTokenService refreshTokenService,
                                                                          @NonNull IdentityTokenProperties properties
    ) {
        return new IssueAccessTokenPairCommandHandler(
                userService,
                principalService,
                tokenFactory,
                tokenExtractor,
                refreshTokenService,
                properties.getAccessToken().getLifetime(),
                properties.getRefreshToken().getLifetime()
        );
    }

    @Bean
    ExtractAndValidateAccessTokenCommandHandler extractAndValidateAccessTokenCommandHandler(@NonNull TokenExtractor extractor) {
        return new ExtractAndValidateAccessTokenCommandHandler(extractor);
    }
}