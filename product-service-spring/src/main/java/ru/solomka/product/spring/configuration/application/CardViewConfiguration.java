package ru.solomka.product.spring.configuration.application;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.product.card.CardViewRepository;
import ru.solomka.product.card.CardViewService;
import ru.solomka.product.card.MinioCardViewRepositoryAdapter;
import ru.solomka.product.minio.MinioComponent;
import ru.solomka.product.minio.MinioValidator;

@Configuration
public class CardViewConfiguration {

    @Bean
    CardViewRepository cardViewRepository(@NonNull MinioComponent minioComponent,
                                         @NonNull MinioValidator minioValidator) {
        return new MinioCardViewRepositoryAdapter(
                minioComponent,
                minioValidator
        );
    }

    @Bean
    CardViewService cardViewService(@NonNull CardViewRepository cardViewRepository) {
        return new CardViewService(cardViewRepository);
    }
}