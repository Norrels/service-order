package com.bytes.service.order.application;

import com.bytes.service.order.application.useCases.product.*;
import com.bytes.service.order.domain.models.ProductCategory;
import com.bytes.service.order.domain.inbound.ProductServicePort;
import com.bytes.service.order.domain.models.Product;

import java.util.List;

public class ProductService implements ProductServicePort {
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final FindProductByCategoryUseCase findProductByCategoryUseCase;
    private final UpdateProductImageUseCase updateProductImageUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;

    public ProductService(CreateProductUseCase createProductUseCase, UpdateProductUseCase updateProductUseCase, DeleteProductUseCase deleteProductUseCase, FindProductByCategoryUseCase findProductByCategoryUseCase, UpdateProductImageUseCase updateProductImageUseCase, FindProductByIdUseCase findProductByIdUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.findProductByCategoryUseCase = findProductByCategoryUseCase;
        this.updateProductImageUseCase = updateProductImageUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
    }

    @Override
    public Product createProduct(Product product) {
        return createProductUseCase.execute(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return updateProductUseCase.execute(id, product);
    }

    @Override
    public void deleteProduct(Long id) {
        deleteProductUseCase.execute(id);
    }

    @Override
    public List<Product> findProductByCategory(ProductCategory category) {
        return findProductByCategoryUseCase.execute(category);
    }

    @Override
    public Product updateImageUrl(Long productId, String imageUrl) {
        return updateProductImageUseCase.execute(productId, imageUrl);
    }

    @Override
    public Product findProductById(Long id) {
        return findProductByIdUseCase.execute(id);
    }
}
