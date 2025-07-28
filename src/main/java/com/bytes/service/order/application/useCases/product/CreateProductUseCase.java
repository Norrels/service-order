package com.bytes.service.order.application.useCases.product;

import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.outbound.ProductRepositoryPort;
import com.bytes.service.order.domain.outbound.UserRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

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
