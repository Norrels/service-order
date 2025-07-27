package com.bytes.service.order.contexts.kitchen.domain.port.inbound;

import com.bytes.service.order.contexts.kitchen.domain.models.Product;
import com.bytes.service.order.contexts.kitchen.domain.models.ProductCategory;

import java.util.List;

public interface ProductServicePort {
    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<Product> findProductByCategory(ProductCategory category);

    Product updateImageUrl(Long productId, String imageUrl);

    Product findProductById(Long id);
}
