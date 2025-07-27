package com.bytes.service.order.contexts.order.domain.models;

import java.math.BigDecimal;

public class OrderItem {
    private Long id;
    private String name;
    private String imgUrl;
    private BigDecimal unitPrice;
    private String category;
    private String description;
    private int quantity;
    private String observation;
    private Long originalProductId;
    private Long orderId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getOriginalProductId() {
        return originalProductId;
    }

    public void setOriginalProductId(Long originalProductId) {
        this.originalProductId = originalProductId;
    }

    public OrderItem(String name, String imgUrl, BigDecimal unitPrice, String category, String description, int quantity, String observation, Long originalProductId) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.unitPrice = unitPrice;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.observation = observation;
        this.originalProductId = originalProductId;

        validate();
    }

    public void validate(){
        if(this.name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("O nome do produto não pode ser nulo");
        }

        if(this.quantity <= 0) {
            throw new IllegalArgumentException("A quantidade do item deve ser maior que 0");
        }

        if(this.unitPrice.compareTo(new BigDecimal("0")) < 1){
            throw new IllegalArgumentException("O preço do produto deve ser maior que 0");
        }

        if(originalProductId == null) {
            throw new IllegalArgumentException("O id do produto original não pode ser nulo");
        }
    }

    public BigDecimal getTotal(){
        return this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
