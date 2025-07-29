package com.bytes.service.order.application;


import com.bytes.service.order.application.useCases.GetOrdersByStatusUseCase;
import com.bytes.service.order.application.useCases.order.*;
import com.bytes.service.order.domain.models.dtos.CreateOrderDTO;
import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderStatus;
import com.bytes.service.order.domain.ports.inbound.OrderServicePort;

import java.util.List;

public class OrderService implements OrderServicePort {
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    private final GetOrdersByStatusUseCase getOrdersByStatusUseCase;

    public OrderService(CreateOrderUseCase createOrderUseCase, GetOrderByIdUseCase getOrderByIdUseCase, CancelOrderUseCase cancelOrderUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase, GetOrdersByStatusUseCase getOrdersByStatusUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.getOrdersByStatusUseCase = getOrdersByStatusUseCase;
    }
    @Override
    public Order createOrder(CreateOrderDTO order) {
        return createOrderUseCase.execute(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return getOrderByIdUseCase.execute(id);
    }

    @Override
    public void cancelOrder(Long id, Long modifyById) {
        cancelOrderUseCase.execute(id);
    }

    @Override
    public void updateStatus(Long id, OrderStatus status, Long modifyById) {
        updateOrderStatusUseCase.execute(id, status, modifyById);
    }

    @Override
    public List<Order> findOrderByStatus(OrderStatus status) {
        return getOrdersByStatusUseCase.execute(status);
    }
}
