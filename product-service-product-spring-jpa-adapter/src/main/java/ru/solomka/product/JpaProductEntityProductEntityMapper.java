package ru.solomka.product;

import ru.solomka.product.common.mapper.Mapper;

public class JpaProductEntityProductEntityMapper implements Mapper<ProductEntity, JpaProductEntity> {
    @Override
    public ProductEntity mapToDomain(JpaProductEntity infrastructureEntity) {
        return ProductEntity.builder()
                .id(infrastructureEntity.getId())
                .name(infrastructureEntity.getName())
                .price(infrastructureEntity.getPrice())
                .description(infrastructureEntity.getDescription())
                .rating(infrastructureEntity.getRating())
                .createdAt(infrastructureEntity.getCreatedAt())
                .build();
    }

    @Override
    public JpaProductEntity mapToInfrastructure(ProductEntity domainEntity) {
        return JpaProductEntity.builder()
                .id(domainEntity.getId())
                .name(domainEntity.getName())
                .price(domainEntity.getPrice())
                .description(domainEntity.getDescription())
                .rating(domainEntity.getRating())
                .createdAt(domainEntity.getCreatedAt())
                .build();
    }
}