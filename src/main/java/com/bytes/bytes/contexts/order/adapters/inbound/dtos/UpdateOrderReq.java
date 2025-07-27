package com.bytes.bytes.contexts.order.adapters.inbound.dtos;

import com.bytes.bytes.contexts.order.domain.models.OrderStatus;

public record UpdateOrderReq(Long id, OrderStatus status) {
}

