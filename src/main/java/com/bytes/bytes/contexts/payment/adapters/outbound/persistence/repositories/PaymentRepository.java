package com.bytes.bytes.contexts.payment.adapters.outbound.persistence.repositories;

import com.bytes.bytes.contexts.payment.adapters.outbound.persistence.entities.PaymentEntity;
import com.bytes.bytes.contexts.payment.domain.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

   Optional<PaymentEntity> findByOrderId(Long orderId);
}
