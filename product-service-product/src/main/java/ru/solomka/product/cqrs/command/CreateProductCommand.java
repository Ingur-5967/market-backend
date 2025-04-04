package ru.solomka.product.cqrs.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateProductCommand {

    @NonNull String name;

    @NonNull String description;

    @NonNull String category;

    byte[] imageBytes;

    @NonNull Integer price;
}