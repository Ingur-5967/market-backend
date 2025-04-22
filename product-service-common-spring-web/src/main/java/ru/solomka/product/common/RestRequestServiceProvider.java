package ru.solomka.product.common;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RestRequestServiceProvider {
    <A, T> ResponseEntity<T> send(HttpMethod method, String uri, Class<T> responseType, Map<String, A> body, RestUriBuilder header);

    <A, T> ResponseEntity<T> getWithBodyParam(String uri, Class<T> responseType, Map<String, A> body);
    <T> ResponseEntity<T> getWithQueryParam(String uri, Class<T> responseType, RestUriBuilder header);

    <T> ResponseEntity<T> emptyGet(String uri, Class<T> responseType);

    <A, T> ResponseEntity<T> postWithBodyParam(String uri, Class<T> responseType, Map<String, A> body);
    <T> ResponseEntity<T> postWithQueryParam(String uri, Class<T> responseType, RestUriBuilder header);
}