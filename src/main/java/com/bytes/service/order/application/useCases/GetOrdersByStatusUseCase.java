package com.bytes.service.order.application.useCases;

import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderStatus;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;

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
