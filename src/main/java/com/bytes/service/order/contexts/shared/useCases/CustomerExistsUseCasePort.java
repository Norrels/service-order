package com.bytes.service.order.contexts.shared.useCases;

public interface CustomerExistsUseCasePort {
    boolean execute(Long id);
}
