package com.bytes.service.order.adapters.inbound.dtos;

import com.bytes.service.order.domain.models.OrderStatus;

public record UpdateOrderReq(Long id, OrderStatus status) {
}

