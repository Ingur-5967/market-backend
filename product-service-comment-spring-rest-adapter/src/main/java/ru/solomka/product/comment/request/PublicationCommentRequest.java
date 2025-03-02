package ru.solomka.product.comment.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublicationCommentRequest {

    @NonNull UUID userId;

    @NonNull UUID productId;

    @NonNull String comment;

    @NonNull Double rating;
}
