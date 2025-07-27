package com.bytes.bytes.contexts.shared.useCases;

import com.bytes.bytes.contexts.shared.dtos.OrderDTO;

public interface GetOrderDTOByIdUseCasePort {
    OrderDTO execute(Long orderId);
}
