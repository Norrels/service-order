package com.bytes.bytes.contexts.payment.domain.port.inbound;

import com.bytes.bytes.contexts.payment.domain.models.Payment;

public interface PaymentServicePort {
    Payment create(Payment payment);
    Payment findByOrderId(Long orderId);
}
