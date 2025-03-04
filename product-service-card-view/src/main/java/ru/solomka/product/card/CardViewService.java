package ru.solomka.product.card;

import lombok.NonNull;
import ru.solomka.product.common.EntityRepository;
import ru.solomka.product.common.EntityService;

public class CardViewService extends EntityService<CardViewEntity> {
    public CardViewService(@NonNull EntityRepository<CardViewEntity> repository) {
        super(repository);
    }
}