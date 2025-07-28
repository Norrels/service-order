package com.bytes.service.order.adapters.inbound.dtos;

import com.bytes.service.order.domain.models.Order;

public record OrderResponseDTO(Order order, Long timeElapsedSinceLastStatus, Long totalTimeElapsed){ }
