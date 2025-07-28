package com.bytes.service.order.domain.outbound;

import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.models.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findByCategory(ProductCategory category);

    void delete(Long id);
}
