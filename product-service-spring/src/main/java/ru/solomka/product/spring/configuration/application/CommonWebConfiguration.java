package ru.solomka.product.spring.configuration.application;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.solomka.product.common.RestRequestServiceProviderAdapter;

@Configuration
public class CommonWebConfiguration {

    @Bean
    public RestRequestServiceProviderAdapter restRequestServiceProviderAdapter(@NonNull RestTemplate restTemplate) {
        return new RestRequestServiceProviderAdapter(restTemplate);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}