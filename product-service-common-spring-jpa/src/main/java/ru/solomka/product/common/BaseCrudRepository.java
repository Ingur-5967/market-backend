package ru.solomka.product.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseCrudRepository<I> extends CrudRepository<I, UUID> {
    Page<I> findAll(Pageable pageable);
}