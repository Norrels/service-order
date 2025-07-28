package com.bytes.service.order.shared.useCases;

import com.bytes.service.order.shared.dtos.OrderDTO;

public interface GetOrderDTOByIdUseCasePort {
    OrderDTO execute(Long orderId);
}
