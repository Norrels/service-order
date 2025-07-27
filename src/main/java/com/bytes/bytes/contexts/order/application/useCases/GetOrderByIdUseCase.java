package com.bytes.bytes.contexts.order.application.useCases;

import com.bytes.bytes.contexts.order.domain.models.Order;
import com.bytes.bytes.contexts.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.bytes.exceptions.ResourceNotFoundException;

public class GetOrderByIdUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    public GetOrderByIdUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    public Order execute(Long id) {
        return orderRepositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }
}
