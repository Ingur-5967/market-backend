package ru.solomka.product.comment.cqrs.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateCommentCommand {

    @NonNull UUID userId;

    @NonNull UUID productId;

    @NonNull String comment;

    @NonNull Double rating;
}