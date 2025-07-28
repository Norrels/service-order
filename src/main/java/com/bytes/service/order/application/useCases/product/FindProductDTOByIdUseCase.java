package com.bytes.service.order.application.useCases.product;

import com.bytes.service.order.domain.outbound.ProductRepositoryPort;
import com.bytes.service.order.utils.ProductMapper;
import com.bytes.service.order.shared.dtos.ProductDTO;
import com.bytes.service.order.shared.useCases.FindProductByIdUseCasePort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

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
