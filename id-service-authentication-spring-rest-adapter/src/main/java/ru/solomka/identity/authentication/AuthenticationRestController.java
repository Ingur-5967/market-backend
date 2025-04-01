package ru.solomka.identity.authentication;

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
import ru.solomka.identity.authentication.cqrs.AuthenticationUserCommand;
import ru.solomka.identity.authentication.cqrs.RegisterUserCommand;
import ru.solomka.identity.authentication.request.SigninUserRequest;
import ru.solomka.identity.authentication.request.SignupUserRequest;
import ru.solomka.identity.authentication.response.AuthenticationResponse;
import ru.solomka.identity.authentication.response.RegistrationResponse;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.token.TokenPair;
import ru.solomka.identity.user.UserEntity;

@RestController
@RequestMapping("/identity/authentication")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationRestController {

    @NonNull
    CommandHandler<RegisterUserCommand, UserEntity> registerUserCommandHandler;

    @NonNull CommandHandler<AuthenticationUserCommand, TokenPair> authenticationUserCommandHandler;

    @Operation(
            summary = "Registration/Create user",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns created user entity",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409", description = "Exception: Operation failed (user already exists and etc.)",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(value = "/signup", produces = "application/json")
    public ResponseEntity<RegistrationResponse> signup(@RequestBody SignupUserRequest signupUserRequest) {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                signupUserRequest.getUsername(),
                signupUserRequest.getPassword(),
                signupUserRequest.getEmail()
        );
        UserEntity userEntity = registerUserCommandHandler.handle(registerUserCommand);
        return ResponseEntity.ok(new RegistrationResponse(
                userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getCreatedAt()
           )
        );
    }

    @Operation(
            summary = "Registration/Create user",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns access and refresh tokens",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400", description = "Exception: Authentication failed (Invalid parameters: pass/login and etc.)",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SigninUserRequest signinUserRequest) {
        AuthenticationUserCommand signinUserCommand = new AuthenticationUserCommand(
                signinUserRequest.getUsername(),
                signinUserRequest.getPassword()
        );
        TokenPair tokenPair = authenticationUserCommandHandler.handle(signinUserCommand);
        return ResponseEntity.ok(new AuthenticationResponse(
                tokenPair.getAccessToken().getToken(),
                tokenPair.getRefreshToken().getToken()
        ));
    }
}