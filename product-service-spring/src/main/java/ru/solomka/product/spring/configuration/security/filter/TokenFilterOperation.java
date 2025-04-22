package ru.solomka.product.spring.configuration.security.filter;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenFilterOperation {

    boolean success;
}
