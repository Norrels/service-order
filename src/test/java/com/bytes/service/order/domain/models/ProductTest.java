package com.bytes.service.order.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product validProduct;
    
    @BeforeEach
    void setUp() {
        validProduct = new Product(1L, "Hambúrguer", "http://example.com/burger.jpg", 
            new BigDecimal("15.99"), ProductCategory.LANCHE, "Delicioso hambúrguer", 
            "Sem cebola", 1L);
    }
    
    @Test
    @DisplayName("Should create product with valid data")
    void shouldCreateProductWithValidData() {
        assertNotNull(validProduct);
        assertEquals(1L, validProduct.getId());
        assertEquals("Hambúrguer", validProduct.getName());
        assertEquals(new BigDecimal("15.99"), validProduct.getPrice());
        assertEquals(ProductCategory.LANCHE, validProduct.getCategory());
        assertEquals(1L, validProduct.getCreatedById());
    }
    
    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(1L, null, "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), ProductCategory.LANCHE, "Description", 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(1L, "", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), ProductCategory.LANCHE, "Description", 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(1L, "   ", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), ProductCategory.LANCHE, "Description", 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when category is null")
    void shouldThrowExceptionWhenCategoryIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(1L, "Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), null, "Description", 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when price is zero")
    void shouldThrowExceptionWhenPriceIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(1L, "Hambúrguer", "http://example.com/burger.jpg", 
                BigDecimal.ZERO, ProductCategory.LANCHE, "Description", 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when price is negative")
    void shouldThrowExceptionWhenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(1L, "Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("-1.0"), ProductCategory.LANCHE, "Description", 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when createdById is null")
    void shouldThrowExceptionWhenCreatedByIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(1L, "Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), ProductCategory.LANCHE, "Description", 
                "Observation", null);
        });
    }
    
    @Test
    @DisplayName("Should update product with valid data")
    void shouldUpdateProductWithValidData() {
        Product updatedProduct = new Product(2L, "Pizza", "http://example.com/pizza.jpg", 
            new BigDecimal("25.99"), ProductCategory.LANCHE, "Deliciosa pizza", 
            "Massa fina", 2L);
        
        validProduct.update(updatedProduct);
        
        assertEquals("Pizza", validProduct.getName());
        assertEquals("http://example.com/pizza.jpg", validProduct.getImgUrl());
        assertEquals(new BigDecimal("25.99"), validProduct.getPrice());
        assertEquals(ProductCategory.LANCHE, validProduct.getCategory());
        assertEquals("Deliciosa pizza", validProduct.getDescription());
        assertEquals("Massa fina", validProduct.getObservation());
    }
    
    @Test
    @DisplayName("Should throw exception when updating with invalid product")
    void shouldThrowExceptionWhenUpdatingWithInvalidProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            Product invalidProduct = new Product(2L, null, "http://example.com/image.jpg", 
                new BigDecimal("10.99"), ProductCategory.LANCHE, "Description", 
                "Observation", 1L);
        });
    }
}