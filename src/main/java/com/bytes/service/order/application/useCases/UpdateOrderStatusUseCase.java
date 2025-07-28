package com.bytes.service.order.application.useCases;

import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderStatus;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

public class UpdateOrderStatusUseCase {
    private final OrderRepositoryPort orderRepositoryPort;

    public UpdateOrderStatusUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    public void execute(Long id, OrderStatus status, Long modifyById) {
        Order order = orderRepositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if(order.getStatus().toString().equals(status.toString())){
            return;
        }

        order.updateStatus(status, modifyById);
        orderRepositoryPort.save(order);
    }
}
