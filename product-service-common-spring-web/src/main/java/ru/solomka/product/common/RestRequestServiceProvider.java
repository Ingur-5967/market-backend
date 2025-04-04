package ru.solomka.product.common;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RestRequestServiceProvider {
    <A> ResponseEntity<?> send(HttpMethod method, String uri, Map<String, A> body);
    <A> ResponseEntity<?> get(String uri, Map<String, A> body);
    ResponseEntity<?> emptyGet(String uri);
    <A> ResponseEntity<?> post(String uri, Map<String, A> body);
}