package com.bytes.service.order.contexts.payment.application.useCases;

import com.bytes.service.order.contexts.payment.domain.models.Payment;
import com.bytes.service.order.contexts.payment.domain.port.oubound.PaymentRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;

public class CreatePaymentUseCase {
    private final PaymentRepositoryPort paymentRepository;

    public CreatePaymentUseCase(PaymentRepositoryPort paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment execute(Payment payment) {
        paymentRepository.findByOrderId(payment.getOrderId()).ifPresent(p -> {
            throw new BusinessException("Já existe um pagamento para o pedido: " + payment.getOrderId());
        });
        return paymentRepository.save(payment);
    }
}
