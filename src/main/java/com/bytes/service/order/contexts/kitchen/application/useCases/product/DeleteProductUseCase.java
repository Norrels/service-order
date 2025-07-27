package com.bytes.service.order.contexts.kitchen.application.useCases.product;

import com.bytes.service.order.contexts.kitchen.domain.port.outbound.ProductRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

public class DeleteProductUseCase {
    private final ProductRepositoryPort productRepository;

    public DeleteProductUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        productRepository.delete(id);
    }
}
