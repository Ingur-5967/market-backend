package ru.solomka.product.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestResponseExtractor {

    static ObjectMapper mapper = new ObjectMapper();

    public static <T> T extractBody(LinkedHashMap<?, ?> body, Class<T> type) {
        return mapper.convertValue(body, type);
    }
}