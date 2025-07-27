package com.bytes.bytes.contexts.order.domain.models;

import com.bytes.bytes.contexts.kitchen.domain.models.ProductCategory;

public enum OrderStatus {
    RECEIVED,
    PREPARING,
    FINISHED,
    ALREADY,
    WAITING_PAYMENT,
    CANCELED;

    public static OrderStatus fromString(String value) {
        try {
            return OrderStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria inválida: " + value);
        }
    }
}
