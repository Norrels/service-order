package com.bytes.service.order.domain.models.dtos;

import java.util.List;
public record CreateOrderDTO(Long clientId, List<OrderItemDTO> itens) { }

