package ru.solomka.product.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.ProductEntity;
import ru.solomka.product.ProductService;
import ru.solomka.product.card.CardViewEntity;
import ru.solomka.product.card.CardViewService;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.exception.EntityAlreadyExistsException;
import ru.solomka.product.cqrs.command.CreateProductCommand;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand, ProductEntity> {

    @NonNull ProductService productService;
    @NonNull CardViewService cardViewService;

    @SneakyThrows
    @Override
    public ProductEntity handle(CreateProductCommand commandEntity) {
        if(productService.findByName(commandEntity.getName()).isPresent())
            throw new EntityAlreadyExistsException("Product with name '%s' already exists".formatted(commandEntity.getName()));

        if (commandEntity.getPrice() < 0)
            throw new IllegalArgumentException("Invalid arguments for create product: Price must be greater that zero");

        if(commandEntity.getDescription().length() > 250)
            throw new IllegalArgumentException("The product description can be no more than 250 characters long");

        ProductEntity createdProduct = ProductEntity.builder()
                .name(commandEntity.getName())
                .description(commandEntity.getDescription())
                .price(commandEntity.getPrice())
                .rating(0.0)
                .build();

        productService.create(createdProduct);

        CardViewEntity previewCardItemEntity = CardViewEntity.builder()
                .id(createdProduct.getId())
                .imageBytes(commandEntity.getImageBytes())
                .createdAt(Instant.now()).build();

        cardViewService.create(previewCardItemEntity);

        return createdProduct;
    }
}