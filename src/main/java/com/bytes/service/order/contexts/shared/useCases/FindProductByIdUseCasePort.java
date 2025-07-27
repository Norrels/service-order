package com.bytes.service.order.contexts.shared.useCases;

import com.bytes.service.order.contexts.shared.dtos.ProductDTO;

public interface FindProductByIdUseCasePort {
    ProductDTO execute(Long id);
}
