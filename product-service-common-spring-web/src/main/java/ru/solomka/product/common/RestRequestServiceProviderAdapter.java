package ru.solomka.product.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestRequestServiceProviderAdapter implements RestRequestServiceProvider {

    @NonNull
    RestTemplate restTemplate;

    @Override
    public <A> ResponseEntity<?> get(String uri, Map<String, A> body) {
        return this.send(HttpMethod.GET, uri, body);
    }

    @Override
    public ResponseEntity<?> emptyGet(String uri) {
        return this.send(HttpMethod.GET, uri, null);
    }

    @Override
    public <A> ResponseEntity<?> post(String uri, Map<String, A> body) {
        return this.send(HttpMethod.POST, uri, body);
    }

    @Override
    public <A> ResponseEntity<?> send(HttpMethod method, String uri, Map<String, A> body) throws RestClientResponseException {
        try {
            return restTemplate.exchange(uri,
                    method,
                    body == null ? HttpEntity.EMPTY : new HttpEntity<>(body),
                    Object.class
            );
        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }
}