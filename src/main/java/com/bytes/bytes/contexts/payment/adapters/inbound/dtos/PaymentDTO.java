package com.bytes.bytes.contexts.payment.adapters.inbound.dtos;

import com.bytes.bytes.contexts.payment.domain.models.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
        private Long order_id;
        private PaymentType paymentType;
        private BigDecimal total;
        private Long external_id;
}
