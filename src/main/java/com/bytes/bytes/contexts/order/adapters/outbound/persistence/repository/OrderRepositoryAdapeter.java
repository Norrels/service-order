package com.bytes.bytes.contexts.order.adapters.outbound.persistence.repository;

import com.bytes.bytes.contexts.order.domain.models.Order;
import com.bytes.bytes.contexts.order.domain.models.OrderStatus;
import com.bytes.bytes.contexts.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.bytes.contexts.order.mappers.OrderMappper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryAdapeter implements OrderRepositoryPort {
    private final OrderRepository repository;

    private final OrderMappper orderMappper;

    public OrderRepositoryAdapeter(OrderRepository repository, OrderMappper orderMappper) {
        this.repository = repository;
        this.orderMappper = orderMappper;
    }

    @Override
    public Order save(Order o) {
        return orderMappper.toOrder(repository.save(orderMappper.toOrderEntity(o)));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(orderMappper::toOrder);
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus status) {
        return repository.findByStatus(status).stream().map(orderMappper::toOrder).toList();
    }
}
