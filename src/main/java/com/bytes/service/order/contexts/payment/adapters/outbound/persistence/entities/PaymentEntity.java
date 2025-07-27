package com.bytes.service.order.contexts.payment.adapters.outbound.persistence.entities;

import com.bytes.service.order.contexts.payment.domain.models.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O Id do pedido é obrigatório")
    private Long orderId;

    @Enumerated
    private PaymentType paymentType;

    @Positive(message = "O valor precisa ser maior que zero")
    private BigDecimal total;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull(message = "O externa ID não pode ser nulo")
    private Long externalId;
}
