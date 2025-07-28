package com.bytes.service.order.application.useCases.product;

import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.models.ProductCategory;
import com.bytes.service.order.domain.outbound.ProductRepositoryPort;

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
