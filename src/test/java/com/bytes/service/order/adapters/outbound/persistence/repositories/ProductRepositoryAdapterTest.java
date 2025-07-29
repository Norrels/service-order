package com.bytes.service.order.adapters.outbound.persistence.repositories;

import com.bytes.service.order.adapters.outbound.persistence.entities.ProductEntity;
import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.models.ProductCategory;
import com.bytes.service.order.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryAdapterTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private ProductMapper productMapper;
    
    @Mock
    private Product mockProduct;
    
    @Mock
    private ProductEntity mockProductEntity;
    
    private ProductRepositoryAdapter productRepositoryAdapter;
    
    @BeforeEach
    void setUp() {
        productRepositoryAdapter = new ProductRepositoryAdapter(productRepository, productMapper);
    }
    
    @Test
    @DisplayName("Should save product successfully")
    void shouldSaveProductSuccessfully() {
        when(productMapper.toProductEntity(mockProduct)).thenReturn(mockProductEntity);
        when(productRepository.save(mockProductEntity)).thenReturn(mockProductEntity);
        when(productMapper.toProduct(mockProductEntity)).thenReturn(mockProduct);
        
        Product result = productRepositoryAdapter.save(mockProduct);
        
        assertEquals(mockProduct, result);
        verify(productMapper).toProductEntity(mockProduct);
        verify(productRepository).save(mockProductEntity);
        verify(productMapper).toProduct(mockProductEntity);
    }
    
    @Test
    @DisplayName("Should find product by ID successfully")
    void shouldFindProductByIDSuccessfully() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProductEntity));
        when(productMapper.toProduct(mockProductEntity)).thenReturn(mockProduct);
        
        Optional<Product> result = productRepositoryAdapter.findById(productId);
        
        assertTrue(result.isPresent());
        assertEquals(mockProduct, result.get());
        verify(productRepository).findById(productId);
        verify(productMapper).toProduct(mockProductEntity);
    }
    
    @Test
    @DisplayName("Should return empty when product not found by ID")
    void shouldReturnEmptyWhenProductNotFoundByID() {
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        
        Optional<Product> result = productRepositoryAdapter.findById(productId);
        
        assertFalse(result.isPresent());
        verify(productRepository).findById(productId);
        verify(productMapper, never()).toProduct(any());
    }
    
    @Test
    @DisplayName("Should find products by category successfully")
    void shouldFindProductsByCategorySuccessfully() {
        ProductCategory category = ProductCategory.LANCHE;
        List<ProductEntity> productEntities = Arrays.asList(mockProductEntity, mockProductEntity);
        List<Product> expectedProducts = Arrays.asList(mockProduct, mockProduct);
        
        when(productRepository.findByCategory(category)).thenReturn(productEntities);
        when(productMapper.toProduct(mockProductEntity)).thenReturn(mockProduct);
        
        List<Product> result = productRepositoryAdapter.findByCategory(category);
        
        assertEquals(2, result.size());
        assertEquals(expectedProducts, result);
        verify(productRepository).findByCategory(category);
        verify(productMapper, times(2)).toProduct(mockProductEntity);
    }
    
    @Test
    @DisplayName("Should return empty list when no products found by category")
    void shouldReturnEmptyListWhenNoProductsFoundByCategory() {
        ProductCategory category = ProductCategory.SOBREMESA;
        when(productRepository.findByCategory(category)).thenReturn(Arrays.asList());
        
        List<Product> result = productRepositoryAdapter.findByCategory(category);
        
        assertTrue(result.isEmpty());
        verify(productRepository).findByCategory(category);
        verify(productMapper, never()).toProduct(any());
    }
    
    @Test
    @DisplayName("Should delete product successfully")
    void shouldDeleteProductSuccessfully() {
        Long productId = 1L;
        
        productRepositoryAdapter.delete(productId);
        
        verify(productRepository).deleteById(productId);
    }
    
    @Test
    @DisplayName("Should handle null product for save")
    void shouldHandleNullProductForSave() {
        when(productMapper.toProductEntity(null)).thenReturn(null);
        when(productRepository.save(null)).thenThrow(new IllegalArgumentException("Product cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            productRepositoryAdapter.save(null);
        });
        
        verify(productMapper).toProductEntity(null);
        verify(productRepository).save(null);
    }
    
    @Test
    @DisplayName("Should handle repository exception for save")
    void shouldHandleRepositoryExceptionForSave() {
        when(productMapper.toProductEntity(mockProduct)).thenReturn(mockProductEntity);
        when(productRepository.save(mockProductEntity)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            productRepositoryAdapter.save(mockProduct);
        });
        
        verify(productMapper).toProductEntity(mockProduct);
        verify(productRepository).save(mockProductEntity);
    }
    
    @Test
    @DisplayName("Should handle repository exception for delete")
    void shouldHandleRepositoryExceptionForDelete() {
        Long productId = 1L;
        doThrow(new RuntimeException("Database error")).when(productRepository).deleteById(productId);
        
        assertThrows(RuntimeException.class, () -> {
            productRepositoryAdapter.delete(productId);
        });
        
        verify(productRepository).deleteById(productId);
    }
    
    @Test
    @DisplayName("Should handle repository exception for findById")
    void shouldHandleRepositoryExceptionForFindById() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            productRepositoryAdapter.findById(productId);
        });
        
        verify(productRepository).findById(productId);
    }
    
    @Test
    @DisplayName("Should handle repository exception for findByCategory")
    void shouldHandleRepositoryExceptionForFindByCategory() {
        ProductCategory category = ProductCategory.LANCHE;
        when(productRepository.findByCategory(category)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            productRepositoryAdapter.findByCategory(category);
        });
        
        verify(productRepository).findByCategory(category);
    }
    
    @Test
    @DisplayName("Should handle mapper exception for save")
    void shouldHandleMapperExceptionForSave() {
        when(productMapper.toProductEntity(mockProduct)).thenThrow(new RuntimeException("Mapper error"));
        
        assertThrows(RuntimeException.class, () -> {
            productRepositoryAdapter.save(mockProduct);
        });
        
        verify(productMapper).toProductEntity(mockProduct);
        verify(productRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Should handle mapper exception for findById")
    void shouldHandleMapperExceptionForFindById() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProductEntity));
        when(productMapper.toProduct(mockProductEntity)).thenThrow(new RuntimeException("Mapper error"));
        
        assertThrows(RuntimeException.class, () -> {
            productRepositoryAdapter.findById(productId);
        });
        
        verify(productRepository).findById(productId);
        verify(productMapper).toProduct(mockProductEntity);
    }
}