package ru.solomka.product.card;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import ru.solomka.product.common.EntityRepository;
import ru.solomka.product.common.EntityService;

import java.time.Instant;

public class CardViewService extends EntityService<CardViewEntity> {
    public CardViewService(@NonNull EntityRepository<CardViewEntity> repository) {
        super(repository);
    }

    @Override
    public @NotNull CardViewEntity create(@NotNull CardViewEntity entity) {
        entity.setCreatedAt(Instant.now());
        return repository.create(entity);
    }
}