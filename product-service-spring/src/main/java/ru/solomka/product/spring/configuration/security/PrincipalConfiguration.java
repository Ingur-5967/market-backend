package ru.solomka.product.spring.configuration.security;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.product.principal.PrincipalRepository;
import ru.solomka.product.principal.PrincipalRepositoryAdapter;
import ru.solomka.product.principal.PrincipalService;

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