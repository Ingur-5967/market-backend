package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.solomka.identity.authentication.*;
import ru.solomka.identity.authentication.cqrs.handler.AuthenticationUserCommandHandler;
import ru.solomka.identity.authentication.cqrs.handler.RegisterUserCommandHandler;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.spring.configuration.properties.IdentityTokenProperties;
import ru.solomka.identity.token.RefreshTokenService;
import ru.solomka.identity.token.TokenPairFactory;
import ru.solomka.identity.user.UserService;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    RegisterUserCommandHandler registerUserCommandHandler(@NonNull PrincipalService principalService,
                                                          @NonNull UserService userService,
                                                          @NonNull EncoderDelegate encoderDelegate) {
        return new RegisterUserCommandHandler(
                principalService,
                userService,
                encoderDelegate
        );
    }

    @Bean
    AuthenticationUserCommandHandler authenticateUserCommandHandler(
            @NotNull AuthenticationService authenticationService,
            @NonNull RefreshTokenService refreshTokenService,
            @NonNull TokenPairFactory tokenPairFactory,
            @NonNull IdentityTokenProperties accessTokenProperties,
            @NonNull IdentityTokenProperties refreshTokenProperties
    ) {
        return new AuthenticationUserCommandHandler(
                authenticationService,
                refreshTokenService,
                tokenPairFactory,
                accessTokenProperties.getAccessToken().getLifetime(),
                refreshTokenProperties.getRefreshToken().getLifetime()
        );
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    EncoderDelegate passwordEncoderDelegate(@NotNull PasswordEncoder passwordEncoder) {
        return new SpringPasswordEncoderDelegateAdapter(passwordEncoder);
    }

    @Bean
    AuthenticationService usernameAuthenticationProvider(
            @NotNull EncoderDelegate passwordEncoderDelegate,
            @NotNull UserService userService,
            @NonNull PrincipalService principalService
    ) {
        return new AuthenticationService(passwordEncoderDelegate, userService, principalService);
    }

    @Bean
    AbstractAuthenticationProvider abstractAuthenticationProvider(@NonNull UserService userService,
                                                                  @NotNull EncoderDelegate encoderDelegate) {
        return new UsernamePasswordAuthenticationProvider(userService, encoderDelegate);
    }
}