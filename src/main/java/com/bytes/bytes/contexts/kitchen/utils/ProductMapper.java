package com.bytes.bytes.contexts.kitchen.utils;

import com.bytes.bytes.contexts.kitchen.adapters.inbound.dtos.ProductRequest;
import com.bytes.bytes.contexts.kitchen.adapters.outbound.persistence.entities.ProductEntity;
import com.bytes.bytes.contexts.kitchen.domain.models.Product;
import com.bytes.bytes.contexts.shared.dtos.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toProductDTO(Product product);

    ProductEntity toProductEntity(Product product);

    Product toProduct(ProductEntity productEntity);

    @Mapping(target = "createdById", source = "userId")
    Product toProduct(ProductRequest productRequest, Long userId);
}
