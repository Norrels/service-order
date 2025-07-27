package com.bytes.service.order.contexts.payment.domain.port.inbound;

import com.bytes.service.order.contexts.payment.domain.models.Payment;

public interface PaymentServicePort {
    Payment create(Payment payment);
    Payment findByOrderId(Long orderId);
}
