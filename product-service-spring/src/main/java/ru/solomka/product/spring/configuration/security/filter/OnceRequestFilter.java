package ru.solomka.product.spring.configuration.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.solomka.product.common.RestRequestServiceProvider;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OnceRequestFilter extends OncePerRequestFilter {

    @NonNull
    RestRequestServiceProvider requestServiceProvider;

    @SuppressWarnings("unchecked")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("Started filter");

        ResponseEntity<Boolean> tokenResponse = (ResponseEntity<Boolean>) requestServiceProvider.post(
                "http://identity-service:8081/identity/tokens/validate",
                Map.of("token", authorizationHeader)
        );

        System.out.println("Token send");

        if(!tokenResponse.hasBody() || Boolean.TRUE.equals(tokenResponse.getBody())) {
            filterChain.doFilter(request, response);
            System.out.println("Started IS NOT VALUID");
            return;
        }

        System.out.println("Token VALID");

        Authentication authenticationToken = new OneTimeTokenAuthenticationToken(authorizationHeader);
        authenticationToken.setAuthenticated(true);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        System.out.println("AUTHENTICATED");
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("/v3/swagger-ui");
    }
}