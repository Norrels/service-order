package com.bytes.service.order.application.useCases;

import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

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
