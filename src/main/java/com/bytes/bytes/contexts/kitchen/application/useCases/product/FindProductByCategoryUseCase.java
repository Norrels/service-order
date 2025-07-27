package com.bytes.bytes.contexts.kitchen.application.useCases.product;

import com.bytes.bytes.contexts.kitchen.domain.models.Product;
import com.bytes.bytes.contexts.kitchen.domain.models.ProductCategory;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.ProductRepositoryPort;

import java.util.List;
public class FindProductByCategoryUseCase {
    private final ProductRepositoryPort productRepository;

    public FindProductByCategoryUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> execute(ProductCategory category) {
        return productRepository.findByCategory(category);
    }
}
