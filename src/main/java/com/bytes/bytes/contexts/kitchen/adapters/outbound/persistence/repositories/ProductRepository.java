package com.bytes.bytes.contexts.kitchen.adapters.outbound.persistence.repositories;

import com.bytes.bytes.contexts.kitchen.adapters.outbound.persistence.entities.ProductEntity;
import com.bytes.bytes.contexts.kitchen.domain.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory(ProductCategory category);
}
