package com.bytes.service.order.contexts.order.adapters.inbound.dtos;

import com.bytes.service.order.contexts.order.domain.models.OrderStatus;

public record UpdateOrderReq(Long id, OrderStatus status) {
}

