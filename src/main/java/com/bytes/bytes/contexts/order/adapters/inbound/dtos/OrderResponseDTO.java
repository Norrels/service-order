package com.bytes.bytes.contexts.order.adapters.inbound.dtos;

import com.bytes.bytes.contexts.order.domain.models.Order;

import java.time.Duration;

public record OrderResponseDTO(Order order, Long timeElapsedSinceLastStatus, Long totalTimeElapsed){ }
