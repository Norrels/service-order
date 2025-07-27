package com.bytes.service.order.contexts.payment.mapper;

import com.bytes.service.order.contexts.payment.adapters.outbound.persistence.entities.PaymentEntity;
import com.bytes.service.order.contexts.payment.domain.models.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPayment(PaymentEntity paymentEntity);
    PaymentEntity toPaymentEntity(Payment payment);
}
