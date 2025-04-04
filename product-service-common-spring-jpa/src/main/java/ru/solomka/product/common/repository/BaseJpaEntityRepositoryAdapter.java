package ru.solomka.product.common.repository;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.solomka.product.common.BaseCrudRepository;
import ru.solomka.product.common.Entity;
import ru.solomka.product.common.EntityRepository;
import ru.solomka.product.common.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseJpaEntityRepositoryAdapter<I, E extends Entity> implements EntityRepository<E> {

    @NonNull
    BaseCrudRepository<I> repository;
    @NonNull Mapper<E, I> mapper;

    @Override
    public E create(E entity) {
        I createdEntity = mapper.mapToInfrastructure(entity);
        createdEntity = repository.save(createdEntity);
        return mapper.mapToDomain(createdEntity);
    }

    @Override
    public E update(E entity) {
        I updatedEntity = mapper.mapToInfrastructure(entity);
        updatedEntity = repository.save(updatedEntity);
        return mapper.mapToDomain(updatedEntity);
    }

    @Override
    public E deleteById(UUID id) {
        I deletedEntity = repository.findById(id).orElseThrow(RuntimeException::new);
        repository.delete(deletedEntity);
        return mapper.mapToDomain(deletedEntity);
    }

    @Override
    public Optional<E> findById(UUID id) {
        return repository.findById(id).map(mapper::mapToDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}