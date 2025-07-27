package com.bytes.bytes.contexts.kitchen.application.useCases.product;

import com.bytes.bytes.contexts.kitchen.domain.models.Product;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.ProductRepositoryPort;
import com.bytes.bytes.exceptions.BusinessException;

public class FindProductByIdUseCase {

    private final ProductRepositoryPort productRepository;

    public FindProductByIdUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado"));
    }
}
