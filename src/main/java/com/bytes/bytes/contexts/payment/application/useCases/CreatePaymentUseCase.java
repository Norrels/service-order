package com.bytes.bytes.contexts.payment.application.useCases;

import com.bytes.bytes.contexts.payment.domain.models.Payment;
import com.bytes.bytes.contexts.payment.domain.port.oubound.PaymentRepositoryPort;
import com.bytes.bytes.exceptions.BusinessException;

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
