package com.bytes.service.order.contexts.order.domain.ports.outbound;

import com.bytes.service.order.contexts.order.domain.models.Order;
import com.bytes.service.order.contexts.order.domain.models.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order o);

    Optional<Order> findById(Long id);

    List<Order> findOrdersByStatus(OrderStatus status);
}
