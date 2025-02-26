package ru.solomka.identity.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.solomka.identity.common.exception.EntityNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class EntityService<T extends Entity> {

    @NonNull EntityRepository<T> repository;

    @NotNull
    public T create(@NotNull T entity) {
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now());
        return repository.create(entity);
    }

    @NotNull
    public T update(@NotNull T entity) throws EntityNotFoundException {
        if (!existsById(entity.getId()))
            throw new EntityNotFoundException("Entity with id %s not found!".formatted(entity.getId()));

        return repository.update(entity);
    }

    public boolean existsById(@NotNull UUID id) {
        return repository.existsById(id);
    }

    public @NotNull T deleteById(@NotNull UUID id) throws EntityNotFoundException {
        if (!existsById(id))
            throw new EntityNotFoundException("Entity with id %s not found!".formatted(id));

        return repository.deleteById(id);
    }

    public @NotNull Optional<T> findById(@NotNull UUID id) {
        return repository.findById(id);
    }

    public @NotNull T getById(@NotNull UUID id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity with id %s not found!".formatted(id))
        );
    }
}
