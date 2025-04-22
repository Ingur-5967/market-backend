package ru.solomka.product.spring.configuration.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.solomka.product.common.RestRequestServiceProvider;
import ru.solomka.product.common.RestResponseExtractor;

import java.io.IOException;
import java.util.LinkedHashMap;
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

        ResponseEntity<TokenFilterOperation> tokenResponse = requestServiceProvider.postWithBodyParam(
                "http://identity-service:8081/identity/tokens/validate",
                TokenFilterOperation.class,
                Map.of("token", authorizationHeader)
        );

        if (!tokenResponse.hasBody() || tokenResponse.getStatusCode() != HttpStatusCode.valueOf(200)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authenticationToken = new OneTimeTokenAuthenticationToken(authorizationHeader);
        authenticationToken.setAuthenticated(true);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("/v3/swagger-ui");
    }
}