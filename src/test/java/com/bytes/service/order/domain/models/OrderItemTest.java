package com.bytes.service.order.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {
    private OrderItem validOrderItem;
    
    @BeforeEach
    void setUp() {
        validOrderItem = new OrderItem("Hambúrguer", "http://example.com/burger.jpg", 
            new BigDecimal("15.99"), "LANCHE", "Delicioso hambúrguer", 2, 
            "Sem cebola", 1L);
    }
    
    @Test
    @DisplayName("Should create order item with valid data")
    void shouldCreateOrderItemWithValidData() {
        assertNotNull(validOrderItem);
        assertEquals("Hambúrguer", validOrderItem.getName());
        assertEquals("http://example.com/burger.jpg", validOrderItem.getImgUrl());
        assertEquals(new BigDecimal("15.99"), validOrderItem.getUnitPrice());
        assertEquals("LANCHE", validOrderItem.getCategory());
        assertEquals("Delicioso hambúrguer", validOrderItem.getDescription());
        assertEquals(2, validOrderItem.getQuantity());
        assertEquals("Sem cebola", validOrderItem.getObservation());
        assertEquals(1L, validOrderItem.getOriginalProductId());
    }
    
    @Test
    @DisplayName("Should calculate total correctly")
    void shouldCalculateTotalCorrectly() {
        BigDecimal expectedTotal = new BigDecimal("15.99").multiply(BigDecimal.valueOf(2));
        assertEquals(expectedTotal, validOrderItem.getTotal());
    }
    
    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem(null, "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), "LANCHE", "Description", 2, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem("", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), "LANCHE", "Description", 2, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem("   ", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), "LANCHE", "Description", 2, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when quantity is zero")
    void shouldThrowExceptionWhenQuantityIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem("Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), "LANCHE", "Description", 0, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when quantity is negative")
    void shouldThrowExceptionWhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem("Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), "LANCHE", "Description", -1, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when unit price is zero")
    void shouldThrowExceptionWhenUnitPriceIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem("Hambúrguer", "http://example.com/burger.jpg", 
                BigDecimal.ZERO, "LANCHE", "Description", 2, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when unit price is negative")
    void shouldThrowExceptionWhenUnitPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem("Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("-1.0"), "LANCHE", "Description", 2, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when original product id is null")
    void shouldThrowExceptionWhenOriginalProductIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem("Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), "LANCHE", "Description", 2, 
                "Observation", null);
        });
    }
    
    @Test
    @DisplayName("Should set and get order id correctly")
    void shouldSetAndGetOrderIdCorrectly() {
        validOrderItem.setOrderId(100L);
        assertEquals(100L, validOrderItem.getOrderId());
    }
    
    @Test
    @DisplayName("Should set and get id correctly")
    void shouldSetAndGetIdCorrectly() {
        validOrderItem.setId(50L);
        assertEquals(50L, validOrderItem.getId());
    }
    
    @Test
    @DisplayName("Should allow null img url")
    void shouldAllowNullImgUrl() {
        assertDoesNotThrow(() -> {
            new OrderItem("Hambúrguer", null, 
                new BigDecimal("15.99"), "LANCHE", "Description", 2, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should allow null description")
    void shouldAllowNullDescription() {
        assertDoesNotThrow(() -> {
            new OrderItem("Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), "LANCHE", null, 2, 
                "Observation", 1L);
        });
    }
    
    @Test
    @DisplayName("Should allow null observation")
    void shouldAllowNullObservation() {
        assertDoesNotThrow(() -> {
            new OrderItem("Hambúrguer", "http://example.com/burger.jpg", 
                new BigDecimal("15.99"), "LANCHE", "Description", 2, 
                null, 1L);
        });
    }
}