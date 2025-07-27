package com.bytes.bytes.contexts.kitchen.domain.models;

public enum ProductCategory {
    LANCHE,
    ACOMPANHAMENTO,
    BEBIDA,
    SOBREMESA;

    public static ProductCategory fromString(String value) {
        try {
            return ProductCategory.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria inválida: " + value);
        }
    }
}
