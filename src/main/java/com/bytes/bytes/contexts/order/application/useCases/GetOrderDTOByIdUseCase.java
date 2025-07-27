package com.bytes.bytes.contexts.order.application.useCases;

import com.bytes.bytes.contexts.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.bytes.contexts.order.mappers.OrderMappper;
import com.bytes.bytes.contexts.shared.dtos.OrderDTO;
import com.bytes.bytes.contexts.shared.useCases.GetOrderDTOByIdUseCasePort;
import com.bytes.bytes.exceptions.ResourceNotFoundException;

public class GetOrderDTOByIdUseCase implements GetOrderDTOByIdUseCasePort {
    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderMappper orderMappper;

    public GetOrderDTOByIdUseCase(OrderRepositoryPort orderRepositoryPort, OrderMappper orderMappper) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderMappper = orderMappper;
    }

    public OrderDTO execute(Long id) {
        return orderRepositoryPort.findById(id).map(orderMappper::toOrderDTO).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }
}
