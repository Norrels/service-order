package com.bytes.service.order.contexts.kitchen.utils;

import com.bytes.service.order.contexts.kitchen.adapters.inbound.dtos.ProductRequest;
import com.bytes.service.order.contexts.kitchen.adapters.outbound.persistence.entities.ProductEntity;
import com.bytes.service.order.contexts.kitchen.domain.models.Product;
import com.bytes.service.order.contexts.shared.dtos.ProductDTO;
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
