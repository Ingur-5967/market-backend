package ru.solomka.identity.token;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.principal.PrincipalEntity;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenFactoryAdapter implements TokenFactory {

    @NotNull JWSSigner jwsSigner;

    @NotNull JWSHeader jwsHeader;

    @Override
    @SneakyThrows
    public @NotNull TokenEntity create(@NotNull PrincipalEntity principalEntity, @NotNull Duration lifetime, @NotNull TokenType tokenType) {
        Date expirationTime = new Date(System.currentTimeMillis() + lifetime.toMillis());
        UUID tokenId = UUID.randomUUID();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(principalEntity.getId().toString())
                .claim(TokenConstraints.JWT_TOKEN_TYPE_NAME, tokenType)
                .claim(TokenConstraints.JWT_TOKEN_ID_NAME, tokenId)
                .issueTime(new Date())
                .expirationTime(expirationTime)
                .build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
        signedJWT.sign(jwsSigner);
        String token = signedJWT.serialize();

        return TokenEntity.builder()
                .id(tokenId).userId(principalEntity.getId())
                .expiredAt(expirationTime.toInstant())
                .token(token)
                .tokenType(tokenType).build();
    }
}