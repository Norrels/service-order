package com.bytes.service.order.application.useCases;

import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.shared.useCases.PayOrderUseCasePort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

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
