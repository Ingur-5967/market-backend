package ru.solomka.product.spring.configuration.application;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.solomka.product.*;
import ru.solomka.product.common.mapper.Mapper;
import ru.solomka.product.cqrs.command.handler.CreateProductCommandHandler;
import ru.solomka.product.cqrs.query.handler.GetProductByIdQueryHandler;
import ru.solomka.product.cqrs.query.handler.GetProductByNameQueryHandler;
import ru.solomka.product.JpaProductEntityProductEntityMapper;

@Configuration
@EntityScan(basePackageClasses = JpaProductEntity.class)
@EnableJpaRepositories(basePackageClasses = JpaProductRepository.class)
public class ProductConfiguration {

    @Bean
    ProductRepository productRepository(@NonNull Mapper<ProductEntity, JpaProductEntity> mapper,
                                        @NonNull JpaProductRepository jpaProductRepository) {
        return new JpaProductEntityRepositoryAdapter(jpaProductRepository, mapper);
    }

    @Bean
    ProductService productService(@NonNull ProductRepository productRepository) {
        return new ProductService(productRepository);
    }

    @Bean
    GetProductByIdQueryHandler getProductByIdQueryHandler(@NonNull ProductService service) {
        return new GetProductByIdQueryHandler(service);
    }

    @Bean
    GetProductByNameQueryHandler getProductByNameQueryHandler(@NonNull ProductService service) {
        return new GetProductByNameQueryHandler(service);
    }

    @Bean
    CreateProductCommandHandler createProductCommandHandler(@NonNull ProductService service) {
        return new CreateProductCommandHandler(service);
    }

    @Bean
    JpaProductEntityProductEntityMapper jpaProductEntityProductEntityMapper() {
        return new JpaProductEntityProductEntityMapper();
    }
}