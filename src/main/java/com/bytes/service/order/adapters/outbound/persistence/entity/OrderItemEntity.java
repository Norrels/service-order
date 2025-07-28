package com.bytes.service.order.adapters.outbound.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "item_pedido")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imgUrl;
    private BigDecimal unitPrice;
    private String category;
    private String description;

    @NotNull
    @Positive
    private int quantity;

    private String observation;

    @NotNull
    private Long originalProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    @JsonIgnore
    private OrderEntity order;

    @JsonProperty(access = Access.READ_ONLY)
    @Column(name = "order_id")
    private Long orderId;
}
