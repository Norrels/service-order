package com.bytes.service.order.contexts.payment.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private Long id;
    private Long orderId;
    private PaymentType paymentType;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private Long externalId;

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getExternalId() {
        return externalId;
    }

    public Payment(Long id, Long orderId, PaymentType paymentType, BigDecimal total, Long externalId) {
        this.id = id;
        this.orderId = orderId;
        this.paymentType = paymentType;
        this.total = total;
        this.createdAt = LocalDateTime.now();
        this.externalId = externalId;
    }
}
