package ru.solomka.product.card.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.card.CardViewEntity;
import ru.solomka.product.card.CardViewService;
import ru.solomka.product.card.cqrs.command.PutImageCardCommand;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.exception.EntityNotFoundException;
import ru.solomka.product.minio.MinioValidator;
import ru.solomka.product.minio.exception.FileAlreadyExistsInContainerException;

import java.nio.file.FileAlreadyExistsException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PutImageCardCommandHandler implements CommandHandler<PutImageCardCommand, CardViewEntity> {

    @NonNull CardViewService cardViewService;

    @NonNull MinioValidator minioValidator;

    @SneakyThrows
    @Override
    public CardViewEntity handle(PutImageCardCommand commandEntity) {
        if(!cardViewService.existsById(commandEntity.getId()))
            throw new EntityNotFoundException("Product with id '%s' not found".formatted(commandEntity.getId()));

        if(minioValidator.existsFile(commandEntity.getId().toString()))
            throw new FileAlreadyExistsInContainerException("Cannot update product view because it already exists");

        CardViewEntity createdCardView = CardViewEntity.builder()
                .id(commandEntity.getId())
                .imageBytes(commandEntity.getImageBytes())
                .build();

        return cardViewService.create(createdCardView);
    }
}