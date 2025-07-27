package com.bytes.service.order.contexts.shared.dtos;

import lombok.Data;

@Data
public class ApiResponseDTO {
    private String message;

    public ApiResponseDTO(String message) {
        this.message = message;
    }
}
