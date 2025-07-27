package com.bytes.service.order.contexts.shared.useCases;

import com.bytes.service.order.contexts.shared.dtos.OrderDTO;

public interface GetOrderDTOByIdUseCasePort {
    OrderDTO execute(Long orderId);
}
