package com.bytes.bytes.contexts.order.application.useCases;

import com.bytes.bytes.contexts.order.domain.models.Order;
import com.bytes.bytes.contexts.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.bytes.contexts.shared.useCases.PayOrderUseCasePort;
import com.bytes.bytes.exceptions.ResourceNotFoundException;

public class PayOrderUseCase implements PayOrderUseCasePort {
    private final OrderRepositoryPort orderRepositoryPort;

    public PayOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }
    @Override
    public void execute(Long id){
        Order order = orderRepositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        order.pay();
        orderRepositoryPort.save(order);
    }
}
