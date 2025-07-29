package com.bytes.service.order.application.useCases.product;

import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.outbound.ProductRepositoryPort;
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
