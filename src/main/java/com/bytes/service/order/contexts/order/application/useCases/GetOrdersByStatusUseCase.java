package com.bytes.service.order.contexts.order.application.useCases;

import com.bytes.service.order.contexts.order.domain.models.Order;
import com.bytes.service.order.contexts.order.domain.models.OrderStatus;
import com.bytes.service.order.contexts.order.domain.ports.outbound.OrderRepositoryPort;

import java.util.List;

public class GetOrdersByStatusUseCase {
    private final OrderRepositoryPort orderRepositoryPort;
    public GetOrdersByStatusUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    public List<Order> execute(OrderStatus status) {
        return orderRepositoryPort.findOrdersByStatus(status);
    }
}
