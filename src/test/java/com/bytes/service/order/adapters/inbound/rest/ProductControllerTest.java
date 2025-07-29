package com.bytes.service.order.adapters.inbound.rest;

import com.bytes.service.order.adapters.inbound.dtos.ProductRequest;
import com.bytes.service.order.application.ProductService;
import com.bytes.service.order.domain.models.Product;
import com.bytes.service.order.domain.models.ProductCategory;
import com.bytes.service.order.domain.outbound.ImageServicePort;
import com.bytes.service.order.infra.config.ExceptionHandlerController;
import com.bytes.service.order.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    
    @Mock
    private ProductService productService;
    
    @Mock
    private ImageServicePort imageService;
    
    @Mock
    private ProductMapper productMapper;
    
    @Mock
    private Product mockProduct;
    
    @Mock
    private MessageSource messageSource;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ProductController productController;
    
    @BeforeEach
    void setUp() {
        productController = new ProductController(productService, imageService, productMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
            .setControllerAdvice(new ExceptionHandlerController(messageSource))
            .build();
        objectMapper = new ObjectMapper();
    }
    
    @Test
    @DisplayName("Should create product successfully")
    void shouldCreateProductSuccessfully() throws Exception {
        ProductRequest productRequest = createValidProductRequest();
        when(productMapper.toProduct(any(ProductRequest.class), eq(1L))).thenReturn(mockProduct);
        when(productService.createProduct(mockProduct)).thenReturn(mockProduct);
        when(mockProduct.getId()).thenReturn(1L);
        when(mockProduct.getName()).thenReturn("Hambúrguer");
        
        mockMvc.perform(post("/kitchen/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest))
                .requestAttr("user_id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Hambúrguer"));
        
        verify(productMapper).toProduct(any(ProductRequest.class), eq(1L));
        verify(productService).createProduct(mockProduct);
    }
    
    @Test
    @DisplayName("Should update product successfully")
    void shouldUpdateProductSuccessfully() throws Exception {
        ProductRequest productRequest = createValidProductRequest();
        when(productMapper.toProduct(any(ProductRequest.class), eq(1L))).thenReturn(mockProduct);
        when(productService.updateProduct(eq(1L), eq(mockProduct))).thenReturn(mockProduct);
        when(mockProduct.getId()).thenReturn(1L);
        when(mockProduct.getName()).thenReturn("Pizza Atualizada");
        
        mockMvc.perform(put("/kitchen/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest))
                .requestAttr("user_id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pizza Atualizada"));
        
        verify(productMapper).toProduct(any(ProductRequest.class), eq(1L));
        verify(productService).updateProduct(1L, mockProduct);
    }
    
    @Test
    @DisplayName("Should delete product successfully")
    void shouldDeleteProductSuccessfully() throws Exception {
        mockMvc.perform(delete("/kitchen/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Produto deletado com sucesso"));
        
        verify(productService).deleteProduct(1L);
    }
    
    @Test
    @DisplayName("Should find products by category successfully")
    void shouldFindProductsByCategorySuccessfully() throws Exception {
        List<Product> products = Arrays.asList(mockProduct);
        when(productService.findProductByCategory(ProductCategory.LANCHE)).thenReturn(products);
        when(mockProduct.getId()).thenReturn(1L);
        when(mockProduct.getName()).thenReturn("Hambúrguer");
        
        mockMvc.perform(get("/kitchen/product/category/LANCHE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Hambúrguer"));
        
        verify(productService).findProductByCategory(ProductCategory.LANCHE);
    }
    
    @Test
    @DisplayName("Should upload image successfully")
    void shouldUploadImageSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "test-image.jpg", 
            MediaType.IMAGE_JPEG_VALUE, 
            "test image content".getBytes()
        );
        
        String imageUrl = "http://example.com/uploaded-image.jpg";
        when(productService.findProductById(1L)).thenReturn(mockProduct);
        when(imageService.uploadImage(any(byte[].class))).thenReturn(imageUrl);
        when(productService.updateImageUrl(1L, imageUrl)).thenReturn(mockProduct);
        when(mockProduct.getId()).thenReturn(1L);
        when(mockProduct.getImgUrl()).thenReturn(imageUrl);
        
        mockMvc.perform(multipart("/kitchen/product/1/upload")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.imgUrl").value(imageUrl));
        
        verify(productService).findProductById(1L);
        verify(imageService).uploadImage(any(byte[].class));
        verify(productService).updateImageUrl(1L, imageUrl);
    }
    
    @Test
    @DisplayName("Should handle image upload failure")
    void shouldHandleImageUploadFailure() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "test-image.jpg", 
            MediaType.IMAGE_JPEG_VALUE, 
            "test image content".getBytes()
        );
        
        when(productService.findProductById(1L)).thenReturn(mockProduct);
        when(imageService.uploadImage(any(byte[].class))).thenThrow(new RuntimeException("Upload failed"));
        
        mockMvc.perform(multipart("/kitchen/product/1/upload")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ocorreu um erro a salvar a imagem"));
        
        verify(productService).findProductById(1L);
        verify(imageService).uploadImage(any(byte[].class));
    }
    
    @Test
    @DisplayName("Should handle invalid category")
    void shouldHandleInvalidCategory() throws Exception {
        mockMvc.perform(get("/kitchen/product/category/INVALID_CATEGORY"))
                .andExpect(status().isUnprocessableEntity());
    }
    
    @Test
    @DisplayName("Should handle missing user_id for create product")
    void shouldHandleMissingUserIdForCreateProduct() throws Exception {
        ProductRequest productRequest = createValidProductRequest();
        
        mockMvc.perform(post("/kitchen/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    @DisplayName("Should handle missing user_id for update product")
    void shouldHandleMissingUserIdForUpdateProduct() throws Exception {
        ProductRequest productRequest = createValidProductRequest();
        
        mockMvc.perform(put("/kitchen/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    @DisplayName("Should handle service exception for create product")
    void shouldHandleServiceExceptionForCreateProduct() throws Exception {
        ProductRequest productRequest = createValidProductRequest();
        when(productMapper.toProduct(any(ProductRequest.class), eq(1L))).thenReturn(mockProduct);
        when(productService.createProduct(mockProduct)).thenThrow(new RuntimeException("Service error"));
        
        mockMvc.perform(post("/kitchen/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest))
                .requestAttr("user_id", 1L))
                .andExpect(status().isInternalServerError());
        
        verify(productService).createProduct(mockProduct);
    }
    
    @Test
    @DisplayName("Should handle service exception for delete product")
    void shouldHandleServiceExceptionForDeleteProduct() throws Exception {
        doThrow(new RuntimeException("Product not found")).when(productService).deleteProduct(999L);
        
        mockMvc.perform(delete("/kitchen/product/999"))
                .andExpect(status().isInternalServerError());
        
        verify(productService).deleteProduct(999L);
    }
    
    private ProductRequest createValidProductRequest() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Hambúrguer");
        productRequest.setPrice(new BigDecimal("15.99"));
        productRequest.setCategory(ProductCategory.LANCHE);
        productRequest.setDescription("Delicioso hambúrguer");
        productRequest.setObservation("Sem cebola");
        productRequest.setImgUrl("http://example.com/burger.jpg");
        return productRequest;
    }
}