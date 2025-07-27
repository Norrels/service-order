package com.bytes.service.order.contexts.payment.domain.port.oubound;

import com.bytes.service.order.contexts.payment.domain.models.Payment;

import java.util.Optional;

public interface PaymentRepositoryPort {
   Payment save(Payment payment);
   Optional<Payment> findByOrderId(Long id);
}
