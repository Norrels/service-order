package com.bytes.service.order.contexts.kitchen.adapters.inbound.dtos;

import com.bytes.service.order.contexts.kitchen.domain.models.ProductCategory;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotNull(message = "O preço do produto é obrigatório")
    @Positive(message = "O preço precisa ser maior que zero")
    private BigDecimal price;

    @NotNull(message = "A categoria é obrigatória")
    private ProductCategory category;

    private String description;

    private String observation;

    private String imgUrl;
}
