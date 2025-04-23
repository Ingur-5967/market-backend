package ru.solomka.product.spring.configuration.application;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solomka.product.common.RestRequestServiceProvider;
import ru.solomka.product.user.UserValidatorWebRequestAdapter;

@Configuration
public class UserSnapshotConfiguration {

    @Bean
    UserValidatorWebRequestAdapter userSnapshotWebRequestSender(@NonNull RestRequestServiceProvider requestServiceProvider) {
        return new UserValidatorWebRequestAdapter(requestServiceProvider);
    }
}