package ru.solomka.identity.spring.configuration.properties;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@ConfigurationProperties(prefix = "service")
@Configuration
public class IdentityTokenProperties {

    @NotNull JwtConfigurationProperties accessToken;

    @NotNull JwtConfigurationProperties refreshToken;

    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class JwtConfigurationProperties {

        @Nullable Duration lifetime;

        @Getter
        @NotNull String privateKeyPath;

        @Getter
        @NotNull String publicKeyPath;

        public @NotNull Duration getLifetime() {
            if (lifetime == null) {
                throw new IllegalStateException("Lifetime not defined");
            }
            return lifetime;
        }
    }
}