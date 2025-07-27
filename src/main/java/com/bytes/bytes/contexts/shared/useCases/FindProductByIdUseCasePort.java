package com.bytes.bytes.contexts.shared.useCases;

import com.bytes.bytes.contexts.shared.dtos.ProductDTO;

public interface FindProductByIdUseCasePort {
    ProductDTO execute(Long id);
}
