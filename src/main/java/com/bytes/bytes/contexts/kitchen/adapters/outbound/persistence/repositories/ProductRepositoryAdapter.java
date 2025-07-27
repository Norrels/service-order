package com.bytes.bytes.contexts.kitchen.adapters.outbound.persistence.repositories;

import com.bytes.bytes.contexts.kitchen.domain.models.Product;
import com.bytes.bytes.contexts.kitchen.domain.models.ProductCategory;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.ProductRepositoryPort;
import com.bytes.bytes.contexts.kitchen.utils.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryAdapter implements ProductRepositoryPort {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductRepositoryAdapter(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product save(Product product) {
        return productMapper.toProduct(productRepository.save(productMapper.toProductEntity(product)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id).map(productMapper::toProduct);
    }

    @Override
    public List<Product> findByCategory(ProductCategory category) {
        return productRepository.findByCategory(category).stream().map(productMapper::toProduct).toList();
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
