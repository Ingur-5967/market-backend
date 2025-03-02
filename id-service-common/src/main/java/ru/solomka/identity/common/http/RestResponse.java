package ru.solomka.identity.common.http;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestResponse<T> {

    T data;

    public static <T> RestResponse<T> from(T data) {
        return new RestResponse<>(data);
    }
}