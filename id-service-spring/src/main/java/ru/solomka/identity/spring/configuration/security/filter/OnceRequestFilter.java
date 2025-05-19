package ru.solomka.identity.spring.configuration.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.solomka.identity.principal.PrincipalEntity;
import ru.solomka.identity.principal.PrincipalService;
import ru.solomka.identity.token.TokenEntity;
import ru.solomka.identity.token.TokenExtractor;
import ru.solomka.identity.user.UserEntity;
import ru.solomka.identity.user.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnceRequestFilter extends OncePerRequestFilter {

    @NonNull
    TokenExtractor tokenExtractor;

    @NonNull
    PrincipalService principalService;

    @NonNull
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenEntity tokenEntity = tokenExtractor.extract(authorizationHeader);

        if(tokenEntity != null) {
            UserEntity userEntity = userService.getById(tokenEntity.getUserId());
            principalService.setPrincipal(PrincipalEntity.builder().id(userEntity.getId()).username(userEntity.getUsername()).build());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("/v3/swagger-ui");
    }
}