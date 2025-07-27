package com.bytes.bytes.contexts.order.adapters.outbound.persistence.repository;

import com.bytes.bytes.contexts.order.adapters.outbound.persistence.entity.OrderEntity;
import com.bytes.bytes.contexts.order.domain.models.Order;
import com.bytes.bytes.contexts.order.domain.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByStatus(OrderStatus status);
}
