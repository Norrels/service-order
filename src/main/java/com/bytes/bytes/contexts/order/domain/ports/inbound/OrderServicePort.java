package com.bytes.bytes.contexts.order.domain.ports.inbound;

import com.bytes.bytes.contexts.order.domain.models.Order;
import com.bytes.bytes.contexts.order.domain.models.dtos.CreateOrderDTO;
import com.bytes.bytes.contexts.order.domain.models.OrderStatus;

import java.util.List;

public interface OrderServicePort {
    Order createOrder(CreateOrderDTO order);

    Order getOrderById(Long id);

    void cancelOrder(Long id, Long modifyById);

    void updateStatus(Long id, OrderStatus status, Long modifyById);

    List<Order> findOrderByStatus(OrderStatus status);
}
