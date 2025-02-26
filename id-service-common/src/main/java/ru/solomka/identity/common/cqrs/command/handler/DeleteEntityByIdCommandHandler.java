package ru.solomka.identity.common.cqrs.command.handler;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.identity.common.Entity;
import ru.solomka.identity.common.EntityService;
import ru.solomka.identity.common.cqrs.CommandHandler;
import ru.solomka.identity.common.cqrs.command.DeleteEntityByIdCommand;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteEntityByIdCommandHandler<T extends Entity> implements CommandHandler<DeleteEntityByIdCommand, T> {

    @NonNull EntityService<T> entityService;

    @Override
    public T handle(DeleteEntityByIdCommand commandEntity) {
        return entityService.deleteById(commandEntity.getId());
    }
}