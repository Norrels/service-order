package com.bytes.service.order.shared.useCases;

public interface PayOrderUseCasePort {
    void execute(Long orderId);
}
