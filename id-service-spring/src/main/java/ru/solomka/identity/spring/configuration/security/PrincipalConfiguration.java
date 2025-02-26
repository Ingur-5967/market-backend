package ru.solomka.identity.spring.configuration.security;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.identity.principal.PrincipalRepository;
import ru.solomka.identity.principal.PrincipalRepositoryAdapter;
import ru.solomka.identity.principal.PrincipalService;

@Configuration
public class PrincipalConfiguration {

    @Bean
    PrincipalRepository principalRepository() {
        return new PrincipalRepositoryAdapter();
    }

    @Bean
    PrincipalService principalService(@NonNull PrincipalRepository principalRepository) {
        return new PrincipalService(principalRepository);
    }
}