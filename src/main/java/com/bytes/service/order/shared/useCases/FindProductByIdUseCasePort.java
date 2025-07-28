package com.bytes.service.order.shared.useCases;

import com.bytes.service.order.shared.dtos.ProductDTO;

public interface FindProductByIdUseCasePort {
    ProductDTO execute(Long id);
}
