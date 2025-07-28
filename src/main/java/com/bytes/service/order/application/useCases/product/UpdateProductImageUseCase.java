package com.bytes.service.order.application.useCases.product;

import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.outbound.ProductRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

public class UpdateProductImageUseCase {
    private final ProductRepositoryPort productRepository;

    public UpdateProductImageUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id, String imageUrl) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        product.setImgUrl(imageUrl);
        productRepository.save(product);
        return product;
    }
}
