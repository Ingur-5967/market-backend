package ru.solomka.product.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestRequestServiceProviderAdapter implements RestRequestServiceProvider {

    @NonNull RestTemplate restTemplate;

    @Override
    public <A, T> ResponseEntity<T> getWithBodyParam(String uri, Class<T> returnedType, Map<String, A> body) {
        return this.send(HttpMethod.GET, uri, returnedType, body, null);
    }

    @Override
    public <T> ResponseEntity<T> getWithQueryParam(String uri, Class<T> returnedType, RestUriBuilder header) {
        return this.send(HttpMethod.GET, uri, returnedType, null, header);
    }

    @Override
    public <T> ResponseEntity<T> emptyGet(String uri, Class<T> returnedType) {
        return this.send(HttpMethod.GET, uri, returnedType, null, null);
    }

    @Override
    public <A, T> ResponseEntity<T> postWithBodyParam(String uri, Class<T> returnedType, Map<String, A> body) {
        return this.send(HttpMethod.POST, uri, returnedType, body, null);
    }

    @Override
    public <T> ResponseEntity<T> postWithQueryParam(String uri, Class<T> returnedType, RestUriBuilder header) {
        return this.send(HttpMethod.POST, uri, returnedType, null, header);
    }

    @Override
    public <A, T> ResponseEntity<T> send(HttpMethod method, String uri, Class<T> returnedType, Map<String, A> body, RestUriBuilder header) throws RestClientResponseException {
        try {
            return restTemplate.exchange(uri,
                    method,
                    body == null ? HttpEntity.EMPTY : new HttpEntity<>(body),
                    returnedType,
                    header == null ? null : header.build()
            );
        } catch (RestClientResponseException e) {
            throw new RuntimeException();
        }
    }
}