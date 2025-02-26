package ru.solomka.identity.spring.configuration.application;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.identity.token.*;

@Configuration
public class TokenNimbusdsAbstractConfiguration {

    @Bean
    TokenFactory tokenFactory(@NonNull JWSSigner signer,
                              @NonNull JWSHeader header) {
        return new TokenFactoryAdapter(signer, header);
    }

    @Bean
    TokenExtractor tokenExtractor(@NonNull JWSVerifier verifier) {
        return new TokenExtractorAdapter(verifier);
    }

    @Bean
    TokenPairFactory tokenPairFactory(@NonNull TokenFactory refreshTokenFactory,
                                      @NonNull TokenFactory accessTokenFactory) {
        return new TokenPairFactory(refreshTokenFactory, accessTokenFactory);
    }
}