package com.bytes.service.order.application;

import com.bytes.service.order.application.useCases.product.*;
import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.models.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private CreateProductUseCase createProductUseCase;
    
    @Mock
    private UpdateProductUseCase updateProductUseCase;
    
    @Mock
    private DeleteProductUseCase deleteProductUseCase;
    
    @Mock
    private FindProductByCategoryUseCase findProductByCategoryUseCase;
    
    @Mock
    private UpdateProductImageUseCase updateProductImageUseCase;
    
    @Mock
    private FindProductByIdUseCase findProductByIdUseCase;
    
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        productService = new ProductService(
            createProductUseCase,
            updateProductUseCase,
            deleteProductUseCase,
            findProductByCategoryUseCase,
            updateProductImageUseCase,
            findProductByIdUseCase
        );
    }
    
    @Test
    @DisplayName("Should create product successfully")
    void shouldCreateProductSuccessfully() {
        Product product = mock(Product.class);
        Product expectedProduct = mock(Product.class);
        
        when(createProductUseCase.execute(product)).thenReturn(expectedProduct);
        
        Product result = productService.createProduct(product);
        
        assertEquals(expectedProduct, result);
        verify(createProductUseCase).execute(product);
    }
    
    @Test
    @DisplayName("Should update product successfully")
    void shouldUpdateProductSuccessfully() {
        Long productId = 1L;
        Product product = mock(Product.class);
        Product expectedProduct = mock(Product.class);
        
        when(updateProductUseCase.execute(productId, product)).thenReturn(expectedProduct);
        
        Product result = productService.updateProduct(productId, product);
        
        assertEquals(expectedProduct, result);
        verify(updateProductUseCase).execute(productId, product);
    }
    
    @Test
    @DisplayName("Should delete product successfully")
    void shouldDeleteProductSuccessfully() {
        Long productId = 1L;
        
        productService.deleteProduct(productId);
        
        verify(deleteProductUseCase).execute(productId);
    }
    
    @Test
    @DisplayName("Should find products by category successfully")
    void shouldFindProductsByCategorySuccessfully() {
        ProductCategory category = ProductCategory.LANCHE;
        List<Product> expectedProducts = Arrays.asList(mock(Product.class), mock(Product.class));
        
        when(findProductByCategoryUseCase.execute(category)).thenReturn(expectedProducts);
        
        List<Product> result = productService.findProductByCategory(category);
        
        assertEquals(expectedProducts, result);
        verify(findProductByCategoryUseCase).execute(category);
    }
    
    @Test
    @DisplayName("Should update product image url successfully")
    void shouldUpdateProductImageUrlSuccessfully() {
        Long productId = 1L;
        String imageUrl = "http://example.com/new-image.jpg";
        Product expectedProduct = mock(Product.class);
        
        when(updateProductImageUseCase.execute(productId, imageUrl)).thenReturn(expectedProduct);
        
        Product result = productService.updateImageUrl(productId, imageUrl);
        
        assertEquals(expectedProduct, result);
        verify(updateProductImageUseCase).execute(productId, imageUrl);
    }
    
    @Test
    @DisplayName("Should find product by id successfully")
    void shouldFindProductByIdSuccessfully() {
        Long productId = 1L;
        Product expectedProduct = mock(Product.class);
        
        when(findProductByIdUseCase.execute(productId)).thenReturn(expectedProduct);
        
        Product result = productService.findProductById(productId);
        
        assertEquals(expectedProduct, result);
        verify(findProductByIdUseCase).execute(productId);
    }
    
    @Test
    @DisplayName("Should handle null product for create")
    void shouldHandleNullProductForCreate() {
        when(createProductUseCase.execute(null)).thenThrow(new IllegalArgumentException("Product cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(null);
        });
        
        verify(createProductUseCase).execute(null);
    }
    
    @Test
    @DisplayName("Should handle null product id for update")
    void shouldHandleNullProductIdForUpdate() {
        Product product = mock(Product.class);
        when(updateProductUseCase.execute(null, product)).thenThrow(new IllegalArgumentException("Product ID cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(null, product);
        });
        
        verify(updateProductUseCase).execute(null, product);
    }
    
    @Test
    @DisplayName("Should handle null product id for delete")
    void shouldHandleNullProductIdForDelete() {
        doThrow(new IllegalArgumentException("Product ID cannot be null"))
            .when(deleteProductUseCase).execute(null);
        
        assertThrows(IllegalArgumentException.class, () -> {
            productService.deleteProduct(null);
        });
        
        verify(deleteProductUseCase).execute(null);
    }
    
    @Test
    @DisplayName("Should handle null category for find by category")
    void shouldHandleNullCategoryForFindByCategory() {
        when(findProductByCategoryUseCase.execute(null)).thenThrow(new IllegalArgumentException("Category cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            productService.findProductByCategory(null);
        });
        
        verify(findProductByCategoryUseCase).execute(null);
    }
    
    @Test
    @DisplayName("Should handle null product id for find by id")
    void shouldHandleNullProductIdForFindById() {
        when(findProductByIdUseCase.execute(null)).thenThrow(new IllegalArgumentException("Product ID cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            productService.findProductById(null);
        });
        
        verify(findProductByIdUseCase).execute(null);
    }
}