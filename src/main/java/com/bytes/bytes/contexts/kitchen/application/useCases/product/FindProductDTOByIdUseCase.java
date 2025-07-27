package com.bytes.bytes.contexts.kitchen.application.useCases.product;

import com.bytes.bytes.contexts.kitchen.domain.port.outbound.ProductRepositoryPort;
import com.bytes.bytes.contexts.kitchen.utils.ProductMapper;
import com.bytes.bytes.contexts.shared.dtos.ProductDTO;
import com.bytes.bytes.contexts.shared.useCases.FindProductByIdUseCasePort;
import com.bytes.bytes.exceptions.ResourceNotFoundException;

public class FindProductDTOByIdUseCase implements FindProductByIdUseCasePort {

    private final ProductRepositoryPort productRepository;

    private final ProductMapper productMapper;

    public FindProductDTOByIdUseCase(ProductRepositoryPort productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO execute(Long id) {
        return productRepository.findById(id).map(productMapper::toProductDTO).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }
}
