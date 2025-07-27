package com.bytes.service.order.contexts.shared.useCases;

public interface PayOrderUseCasePort {
    void execute(Long orderId);
}
