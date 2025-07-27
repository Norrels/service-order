package com.bytes.service.order.contexts.order.adapters.inbound.dtos;

import com.bytes.service.order.contexts.order.domain.models.Order;

public record OrderResponseDTO(Order order, Long timeElapsedSinceLastStatus, Long totalTimeElapsed){ }
