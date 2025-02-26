package ru.solomka.identity.spring.configuration.external;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.solomka.identity.kafka.event.UserCreatedEvent;
import ru.solomka.identity.kafka.event.UserDeletedEvent;
import ru.solomka.identity.kafka.event.UserUpdatedEvent;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaTemplate<String, UserCreatedEvent> userCreatedEventKafkaTemplate(@NotNull ProducerFactory<String, UserCreatedEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, UserUpdatedEvent> userUpdatedEventKafkaTemplate(@NotNull ProducerFactory<String, UserUpdatedEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, UserDeletedEvent> userDeletedEventKafkaTemplate(@NotNull ProducerFactory<String, UserDeletedEvent> factory) {

        return new KafkaTemplate<>(factory);
    }
}