package com.bytes.bytes.contexts.payment.mapper;

import com.bytes.bytes.contexts.customer.mapper.CustomerMapper;
import com.bytes.bytes.contexts.payment.adapters.outbound.persistence.entities.PaymentEntity;
import com.bytes.bytes.contexts.payment.domain.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPayment(PaymentEntity paymentEntity);
    PaymentEntity toPaymentEntity(Payment payment);
}
