package ru.solomka.identity.common.mapper;

public interface Mapper<D, I> {
    D mapToDomain(I infrastructureEntity);
    I mapToInfrastructure(D domainEntity);
}
