package com.bytes.service.order.contexts.order.domain.models.dtos;

import java.util.List;
public record CreateOrderDTO(Long clientId, List<OrderItemDTO> itens) { }

