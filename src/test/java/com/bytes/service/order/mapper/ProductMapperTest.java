package com.bytes.service.order.mapper;

import com.bytes.service.order.adapters.inbound.dtos.ProductRequest;
import com.bytes.service.order.adapters.outbound.persistence.entities.ProductEntity;
import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.models.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    @DisplayName("Should map Product domain model to ProductEntity")
    void shouldMapProductToProductEntity() {
        Product product = new Product(
            1L,
            "Hambúrguer",
            "http://example.com/burger.jpg",
            new BigDecimal("15.99"),
            ProductCategory.LANCHE,
            "Delicioso hambúrguer",
            "Sem cebola",
            2L
        );

        ProductEntity entity = productMapper.toProductEntity(product);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Hambúrguer", entity.getName());
        assertEquals("http://example.com/burger.jpg", entity.getImgUrl());
        assertEquals(new BigDecimal("15.99"), entity.getPrice());
        assertEquals(ProductCategory.LANCHE, entity.getCategory());
        assertEquals("Delicioso hambúrguer", entity.getDescription());
        assertEquals("Sem cebola", entity.getObservation());
        assertEquals(2L, entity.getCreatedById());
    }

    @Test
    @DisplayName("Should map ProductEntity to Product domain model")
    void shouldMapProductEntityToProduct() {
        ProductEntity entity = ProductEntity.builder()
            .id(1L)
            .name("Refrigerante")
            .imgUrl("http://example.com/drink.jpg")
            .price(new BigDecimal("5.99"))
            .category(ProductCategory.BEBIDA)
            .description("Refrigerante gelado")
            .observation("Com gelo")
            .createdById(3L)
            .build();

        Product product = productMapper.toProduct(entity);

        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Refrigerante", product.getName());
        assertEquals("http://example.com/drink.jpg", product.getImgUrl());
        assertEquals(new BigDecimal("5.99"), product.getPrice());
        assertEquals(ProductCategory.BEBIDA, product.getCategory());
        assertEquals("Refrigerante gelado", product.getDescription());
        assertEquals("Com gelo", product.getObservation());
        assertEquals(3L, product.getCreatedById());
    }

    @Test
    @DisplayName("Should map ProductRequest to Product domain model with userId")
    void shouldMapProductRequestToProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("Pizza");
        request.setImgUrl("http://example.com/pizza.jpg");
        request.setPrice(new BigDecimal("25.99"));
        request.setCategory(ProductCategory.LANCHE);
        request.setDescription("Deliciosa pizza");
        request.setObservation("Massa fina");
        
        Long userId = 4L;

        Product product = productMapper.toProduct(request, userId);

        assertNotNull(product);
        assertEquals("Pizza", product.getName());
        assertEquals("http://example.com/pizza.jpg", product.getImgUrl());
        assertEquals(new BigDecimal("25.99"), product.getPrice());
        assertEquals(ProductCategory.LANCHE, product.getCategory());
        assertEquals("Deliciosa pizza", product.getDescription());
        assertEquals("Massa fina", product.getObservation());
        assertEquals(4L, product.getCreatedById());
    }

    @Test
    @DisplayName("Should handle null Product domain model")
    void shouldHandleNullProduct() {
        ProductEntity entity = productMapper.toProductEntity(null);
        assertNull(entity);
    }

    @Test
    @DisplayName("Should handle null ProductEntity")
    void shouldHandleNullProductEntity() {
        Product product = productMapper.toProduct((ProductEntity) null);
        assertNull(product);
    }

    @Test
    @DisplayName("Should handle null ProductRequest")
    void shouldHandleNullProductRequest() {
        assertThrows(IllegalArgumentException.class, () -> {
            productMapper.toProduct(null, 1L);
        });
    }

    @Test
    @DisplayName("Should map ProductEntity with minimal data")
    void shouldMapProductEntityWithMinimalData() {
        ProductEntity entity = ProductEntity.builder()
            .id(5L)
            .name("Produto Simples")
            .price(new BigDecimal("10.00"))
            .category(ProductCategory.LANCHE)
            .createdById(1L)
            .build();

        Product product = productMapper.toProduct(entity);

        assertNotNull(product);
        assertEquals(5L, product.getId());
        assertEquals("Produto Simples", product.getName());
        assertNull(product.getImgUrl());
        assertEquals(new BigDecimal("10.00"), product.getPrice());
        assertEquals(ProductCategory.LANCHE, product.getCategory());
        assertNull(product.getDescription());
        assertNull(product.getObservation());
        assertEquals(1L, product.getCreatedById());
    }

    @Test
    @DisplayName("Should map Product with all product categories")
    void shouldMapProductWithAllCategories() {
        for (ProductCategory category : ProductCategory.values()) {
            Product product = new Product(
                1L,
                "Test Product",
                "http://example.com/test.jpg",
                new BigDecimal("20.00"),
                category,
                "Test description",
                "Test observation",
                1L
            );

            ProductEntity entity = productMapper.toProductEntity(product);

            assertNotNull(entity);
            assertEquals(category, entity.getCategory());
        }
    }
}