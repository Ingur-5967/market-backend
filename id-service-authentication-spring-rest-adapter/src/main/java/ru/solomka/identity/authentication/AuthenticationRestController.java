package ru.solomka.identity.authentication;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
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
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.token.TokenPair;

@RestController
@RequestMapping("/identity")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationRestController {

    @NonNull
    CommandHandler<RegisterUserCommand, TokenPair> registerUserCommandHandler;

    @NonNull CommandHandler<AuthenticationUserCommand, TokenPair> authenticationUserCommandHandler;

    @PostMapping(value = "/authentication/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignupUserRequest signupUserRequest) {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                signupUserRequest.getUsername(),
                signupUserRequest.getPassword(),
                signupUserRequest.getEmail()
        );
        TokenPair tokenPair = registerUserCommandHandler.handle(registerUserCommand);
        return ResponseEntity.ok(new AuthenticationResponse(
                tokenPair.getAccessToken().getToken(),
                tokenPair.getRefreshToken().getToken()
        ));
    }

    @PostMapping(value = "/authentication/signin", produces = MediaType.APPLICATION_JSON_VALUE)
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