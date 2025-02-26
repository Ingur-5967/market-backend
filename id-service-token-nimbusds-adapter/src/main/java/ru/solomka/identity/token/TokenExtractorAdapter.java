package ru.solomka.identity.token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.token.exception.TokenException;
import ru.solomka.identity.token.exception.TokenExpiredException;
import ru.solomka.identity.token.exception.TokenParseException;
import ru.solomka.identity.token.exception.TokenVerificationException;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenExtractorAdapter implements TokenExtractor {

    @NotNull JWSVerifier jwsVerifier;

    @Override
    public @NotNull TokenEntity extract(@NotNull String token) throws TokenException {
        try {
            if(!token.startsWith("Bearer"))
                throw new TokenParseException("Invalid or corrupt token: %s".formatted(token));

            token = token.replace("Bearer ", "");
            SignedJWT signedJWT = SignedJWT.parse(token);

            if (!signedJWT.verify(jwsVerifier))
                throw new TokenParseException("Invalid or corrupt token signature: %s".formatted(token));

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationDate = claims.getExpirationTime();

            if (expirationDate == null || Instant.now().isAfter(expirationDate.toInstant()))
                throw new TokenExpiredException("Token has expired");

            UUID tokenId = UUID.fromString(claims.getStringClaim(TokenConstraints.JWT_TOKEN_ID_NAME));
            UUID userId = UUID.fromString(claims.getSubject());
            Instant expirationTime = expirationDate.toInstant();
            TokenType type = TokenType.valueOf(claims.getStringClaim(TokenConstraints.JWT_TOKEN_TYPE_NAME));

            return TokenEntity.builder()
                    .id(tokenId)
                    .userId(userId)
                    .expiredAt(expirationTime)
                    .token(token).tokenType(type).build();

        } catch (ParseException exception) {
            throw new TokenParseException(exception.getMessage());
        } catch (JOSEException exception) {
            throw new TokenVerificationException(exception.getMessage());
        } catch (Exception exception) {
            throw new TokenParseException("Invalid or corrupt token: %s".formatted(token));
        }
    }
}
