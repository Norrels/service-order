package com.bytes.bytes.contexts.order.application.useCases;

import com.bytes.bytes.contexts.order.domain.models.Order;
import com.bytes.bytes.contexts.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.bytes.exceptions.ResourceNotFoundException;

public class CancelOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    public CancelOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    public void execute(Long id) {
        Order order = orderRepositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        order.cancel();
        orderRepositoryPort.save(order);
    }
}
