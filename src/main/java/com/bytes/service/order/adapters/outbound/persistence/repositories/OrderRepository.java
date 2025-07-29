package com.bytes.service.order.adapters.outbound.persistence.repositories;

import com.bytes.service.order.adapters.outbound.persistence.entities.OrderEntity;
import com.bytes.service.order.domain.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByStatus(OrderStatus status);
}
