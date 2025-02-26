package ru.solomka.identity.spring.configuration.external;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.solomka.identity.spring.configuration.properties.IdentityTokenProperties;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class NimbusdsConfiguration {

    @Bean
    @SneakyThrows
    PrivateKey accessTokenPrivateKey(
            @NotNull IdentityTokenProperties properties,
            @NotNull ResourceLoader resourceLoader,
            @NotNull KeyFactory keyFactory
    ) {
        Resource resource = resourceLoader.getResource(properties.getAccessToken().getPrivateKeyPath());
        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(content));
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    @Bean
    @SneakyThrows
    PublicKey accessTokenPublicKey(
            @NotNull IdentityTokenProperties properties,
            @NotNull ResourceLoader resourceLoader,
            @NotNull KeyFactory keyFactory
    ) {
        Resource resource = resourceLoader.getResource(properties.getAccessToken().getPublicKeyPath());
        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(content));
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    @Bean
    JWSSigner accessTokenJwsSigner(
            @NotNull @Qualifier("accessTokenPrivateKey") PrivateKey accessTokenPrivateKey
    ) {
        return new RSASSASigner(accessTokenPrivateKey);
    }

    @Bean
    JWSVerifier accessTokenJwsVerifier(
            @NotNull @Qualifier("accessTokenPublicKey") PublicKey publicKey
    ) {
        return new RSASSAVerifier((RSAPublicKey) publicKey);
    }

    @Bean
    JWSHeader jwsHeader() {
        return new JWSHeader.Builder(JWSAlgorithm.RS256).build();
    }
}