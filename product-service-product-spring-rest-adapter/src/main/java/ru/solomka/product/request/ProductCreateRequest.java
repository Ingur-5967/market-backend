package ru.solomka.product.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCreateRequest {

    @NonNull String name;

    @NonNull String description;

    @NonNull Integer price;

    @NonNull MultipartFile image;
}