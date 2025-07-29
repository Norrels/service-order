package com.bytes.service.order.application.useCases.order;

import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderItem;
import com.bytes.service.order.domain.models.dtos.CreateOrderDTO;
import com.bytes.service.order.domain.outbound.ProductRepositoryPort;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

import java.util.List;

public class CreateOrderUseCase {
    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    public CreateOrderUseCase(OrderRepositoryPort orderRepositoryPort, CustomerRepositoryPort customerRepositoryPort, ProductRepositoryPort productRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.customerRepositoryPort = customerRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    // poderia ser só uma lista de OrderItemDTO
    public Order execute(CreateOrderDTO order) {
        if(order.clientId() != null && customerRepositoryPort.findById(order.clientId()).isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        List<OrderItem> orderItens = order.itens().stream().map(orderItem -> {
            var product = productRepositoryPort.findById(orderItem.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + orderItem.productId()));
            return new OrderItem(product.getName(), product.getImgUrl(), product.getPrice(), product.getCategory().toString(), product.getDescription(), orderItem.quantity(), product.getObservation(), product.getId());
        }).toList();

        Order newOrder = orderRepositoryPort.save(new Order(order.clientId(), orderItens));
        newOrder.assignOrderIdToItems();

        return orderRepositoryPort.save(newOrder);
    }
}