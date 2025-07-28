package com.bytes.service.order.application.useCases;

import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.mapper.OrderMappper;
import com.bytes.service.order.shared.dtos.OrderDTO;
import com.bytes.service.order.shared.useCases.GetOrderDTOByIdUseCasePort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

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
