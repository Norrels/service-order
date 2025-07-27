package com.bytes.service.order.contexts.kitchen.adapters.outbound.persistence.entities;

import com.bytes.service.order.contexts.kitchen.domain.models.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "product_entity")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome é obrigatório")
    private String name;
    private String imgUrl;

    @Positive(message = "Preço deve ser maior que zero")
    @NotNull(message = "Preço é obrigatório")
    private BigDecimal price;

    @NotNull(message = "Categoria é obrigatória")
    private ProductCategory category;
    private String description;
    private String observation;

    @NotNull(message = "O usuário que criou o produto é obrigatório")
    private Long createdById;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdById", insertable = false, updatable = false)
    private UserEntity userEntity;
}
