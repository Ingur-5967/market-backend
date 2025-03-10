package ru.solomka.product.card.cqrs.query.handler;


import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.card.CardViewEntity;
import ru.solomka.product.card.CardViewService;
import ru.solomka.product.card.cqrs.query.GetImageCardByIdQuery;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.exception.EntityNotFoundException;
import ru.solomka.product.minio.MinioValidator;

import java.io.FileNotFoundException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetImageCardByIdQueryHandler implements CommandHandler<GetImageCardByIdQuery, byte[]> {

    @NonNull CardViewService cardViewService;

    @NonNull MinioValidator minioValidator;

    @SneakyThrows
    @Override
    public byte[] handle(GetImageCardByIdQuery commandEntity) {
        if(!cardViewService.existsById(commandEntity.getId()))
            throw new EntityNotFoundException("Product with id '%s' not found".formatted(commandEntity.getId()));

        if(!minioValidator.existsFile(commandEntity.getId().toString()))
            throw new FileNotFoundException("File with name '%s' not found in bucket".formatted(commandEntity.getId()));

        CardViewEntity cardViewEntity = cardViewService.findById(commandEntity.getId()).orElseThrow(RuntimeException::new);

        return cardViewEntity.getImageBytes();
    }
}
