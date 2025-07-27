package com.bytes.bytes.contexts.kitchen.application.useCases.product;

import com.bytes.bytes.contexts.kitchen.domain.models.Product;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.ProductRepositoryPort;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.UserRepositoryPort;
import com.bytes.bytes.exceptions.ResourceNotFoundException;

public class CreateProductUseCase {
    private final ProductRepositoryPort productRepository;
    private final UserRepositoryPort userRepository;

    public CreateProductUseCase(ProductRepositoryPort productRepository, UserRepositoryPort userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Product execute(Product product) {
        userRepository.findById(product.getCreatedById()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return productRepository.save(product);
    }
}
