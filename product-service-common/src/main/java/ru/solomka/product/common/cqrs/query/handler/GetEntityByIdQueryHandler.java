package ru.solomka.product.common.cqrs.query.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.common.Entity;
import ru.solomka.product.common.EntityService;
import ru.solomka.product.common.cqrs.CommandHandler;
import ru.solomka.product.common.cqrs.query.GetEntityByIdQuery;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetEntityByIdQueryHandler<T extends Entity> implements CommandHandler<GetEntityByIdQuery, T> {

    @NonNull
    EntityService<T> entityService;

    @Override
    public T handle(GetEntityByIdQuery commandEntity) {
        return entityService.getById(commandEntity.getId());
    }
}