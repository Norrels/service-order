package com.bytes.service.order.contexts.kitchen.application.useCases.product;

import com.bytes.service.order.contexts.kitchen.domain.models.Product;
import com.bytes.service.order.contexts.kitchen.domain.port.outbound.ProductRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;

public class FindProductByIdUseCase {

    private final ProductRepositoryPort productRepository;

    public FindProductByIdUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado"));
    }
}
