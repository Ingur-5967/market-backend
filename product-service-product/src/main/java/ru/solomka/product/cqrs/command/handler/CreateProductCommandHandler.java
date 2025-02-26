package ru.solomka.product.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.ProductEntity;
import ru.solomka.product.ProductService;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.cqrs.command.CreateProductCommand;
import ru.solomka.product.exception.ProductOperationException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand, ProductEntity> {

    @NonNull ProductService productService;

    @SneakyThrows
    @Override
    public ProductEntity handle(CreateProductCommand commandEntity) {
        if(productService.findByName(commandEntity.getName()).isPresent())
            throw new ProductOperationException("Product with name '%s' already exists".formatted(commandEntity.getName()));

        if (commandEntity.getPrice() < 0)
            throw new ProductOperationException("Invalid arguments for create product: Price must be greater that zero");

        if(commandEntity.getDescription().length() > 250)
            throw new ProductOperationException("The product description can be no more than 250 characters long");

        ProductEntity createdProduct = ProductEntity.builder()
                .name(commandEntity.getName())
                .description(commandEntity.getDescription())
                .price(commandEntity.getPrice())
                .rating(0.0)
                .build();

        return productService.create(createdProduct);
    }
}