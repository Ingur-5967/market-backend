package ru.solomka.product.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestUriBuilder {

    @NonNull UriComponentsBuilder uriBuilder;

    private RestUriBuilder(String uri) {
        this.uriBuilder = UriComponentsBuilder.fromUriString(uri);
    }

    public RestUriBuilder queryParam(String key, Object value) {
        uriBuilder.queryParam(key, value);
        return this;
    }

    public static RestUriBuilder create(String uri) {
        return new RestUriBuilder(uri);
    }

    public String build() {
        return uriBuilder.toUriString();
    }
}
