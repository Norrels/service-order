package com.bytes.service.order.contexts.order.application.useCases;

import com.bytes.service.order.contexts.order.domain.models.Order;
import com.bytes.service.order.contexts.order.domain.models.OrderItem;
import com.bytes.service.order.contexts.order.domain.models.dtos.CreateOrderDTO;
import com.bytes.service.order.contexts.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.contexts.shared.useCases.CustomerExistsUseCasePort;
import com.bytes.service.order.contexts.shared.useCases.FindProductByIdUseCasePort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

import java.util.List;

public class CreateOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerExistsUseCasePort findUserByIdUseCase;
    private final FindProductByIdUseCasePort findProductByIdUseCase;

    public CreateOrderUseCase(OrderRepositoryPort orderRepositoryPort, CustomerExistsUseCasePort findUserByIdUseCase, FindProductByIdUseCasePort findProductByIdUseCase) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
    }

    // poderia ser só uma lista de OrderItemDTO
    public Order execute(CreateOrderDTO order) {
        if(order.clientId() != null && !findUserByIdUseCase.execute(order.clientId())) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        List<OrderItem> orderItens = order.itens().stream().map(orderItem -> {
            var product = findProductByIdUseCase.execute(orderItem.productId());
            return new OrderItem(product.getName(), product.getImgUrl(), product.getPrice(), product.getCategory().toString(), product.getDescription(), orderItem.quantity(), product.getObservation(), product.getId());
        }).toList();

        Order newOrder = orderRepositoryPort.save(new Order(order.clientId(), orderItens));
        newOrder.assignOrderIdToItems();

        return orderRepositoryPort.save(newOrder);
    }
}