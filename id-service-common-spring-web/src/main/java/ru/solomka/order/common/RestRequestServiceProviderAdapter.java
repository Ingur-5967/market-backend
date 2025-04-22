package ru.solomka.order.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestRequestServiceProviderAdapter implements RestRequestServiceProvider {

    @NonNull RestTemplate restTemplate;

    @Override
    public <A> ResponseEntity<?> getWithBodyParam(String uri, Map<String, A> body) {
        return this.send(HttpMethod.GET, uri, body, null);
    }

    @Override
    public ResponseEntity<?> getWithQueryParam(String uri, RestUriBuilder header) {
        return this.send(HttpMethod.GET, uri, null, header);
    }

    @Override
    public ResponseEntity<?> emptyGet(String uri) {
        return this.send(HttpMethod.GET, uri, null, null);
    }

    @Override
    public <A> ResponseEntity<?> postWithBodyParam(String uri, Map<String, A> body) {
        return this.send(HttpMethod.POST, uri, body, null);
    }

    @Override
    public ResponseEntity<?> postWithQueryParam(String uri, RestUriBuilder header) {
        return this.send(HttpMethod.POST, uri, null, header);
    }

    @Override
    public <A> ResponseEntity<?> send(HttpMethod method, String uri, Map<String, A> body, RestUriBuilder header) throws RestClientResponseException {
        try {
            return restTemplate.exchange(uri,
                    method,
                    body == null ? HttpEntity.EMPTY : new HttpEntity<>(body),
                    Object.class,
                    header == null ? null : header.build()
            );
        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}