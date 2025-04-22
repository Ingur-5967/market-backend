package ru.solomka.order.common;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public interface RestRequestServiceProvider {
    <A> ResponseEntity<?> send(HttpMethod method, String uri, Map<String, A> body, RestUriBuilder header);

    <A> ResponseEntity<?> getWithBodyParam(String uri, Map<String, A> body);
    ResponseEntity<?> getWithQueryParam(String uri, RestUriBuilder header);

    ResponseEntity<?> emptyGet(String uri);

    <A> ResponseEntity<?> postWithBodyParam(String uri, Map<String, A> body);
    ResponseEntity<?> postWithQueryParam(String uri, RestUriBuilder header);
}