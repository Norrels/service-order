package com.bytes.service.order.application.useCases;

import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

public class GetOrderByIdUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    public GetOrderByIdUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    public Order execute(Long id) {
        return orderRepositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }
}
