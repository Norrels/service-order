package com.bytes.bytes.contexts.order.domain.models.dtos;

import java.util.List;
public record CreateOrderDTO(Long clientId, List<OrderItemDTO> itens) { }

