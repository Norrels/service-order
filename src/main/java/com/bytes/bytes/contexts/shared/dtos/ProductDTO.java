package com.bytes.bytes.contexts.shared.dtos;


import com.bytes.bytes.contexts.kitchen.domain.models.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String imgUrl;
    private BigDecimal price;
    private ProductCategory category;
    private String description;
    private Long createdById;
    private String observation;
}
