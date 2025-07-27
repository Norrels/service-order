package com.bytes.service.order.contexts.kitchen.application.useCases.product;

import com.bytes.service.order.contexts.kitchen.domain.models.Product;
import com.bytes.service.order.contexts.kitchen.domain.port.outbound.ProductRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;


public class UpdateProductUseCase {
    private final ProductRepositoryPort productRepository;

    public UpdateProductUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id, Product productToUpdate) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        product.update(productToUpdate);
        productRepository.save(product);
        return product;
    }
}
