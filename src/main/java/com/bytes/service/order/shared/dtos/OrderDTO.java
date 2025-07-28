package com.bytes.service.order.shared.dtos;

import com.bytes.service.order.domain.models.OrderItem;
import com.bytes.service.order.domain.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;
    private Long modifyById;
    private Long clientId;
    private List<OrderItem> itens;
}
