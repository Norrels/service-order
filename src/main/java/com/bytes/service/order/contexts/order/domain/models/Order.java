package com.bytes.service.order.contexts.order.domain.models;

import com.bytes.service.order.exceptions.BusinessException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private OrderStatus status;

    private Long modifyById;

    private Long clientId;

    private List<OrderItem> itens;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getModifyById() {
        return modifyById;
    }

    public Order(Long clientId, List<OrderItem> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido deve ter pelo menos um item");
        }

        for (OrderItem item : itens) {
            item.validate();
        }

        this.clientId = clientId;
        this.itens = itens;
        this.status = OrderStatus.WAITING_PAYMENT;
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    public Order(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, OrderStatus status, Long modifyById, Long clientId, List<OrderItem> itens) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.modifyById = modifyById;
        this.clientId = clientId;
        this.itens = itens;
    }

    public void updateStatus(OrderStatus status, Long modifyById) {
        if(status == OrderStatus.WAITING_PAYMENT || status == OrderStatus.CANCELED) {
            throw new BusinessException("Operação invalída");
        }

        if(this.status == OrderStatus.WAITING_PAYMENT) {
            throw new BusinessException("Não é possível alterar o status do pedido que ainda não foi pago");
        }

        if(modifyById == null) {
            throw new IllegalArgumentException("É preciso informar o usuário que está alterando o status do pedido");
        }

        this.status = status;
        this.updatedAt = LocalDateTime.now();
        this.modifyById = modifyById;
    }

    public void pay(){
        if(this.status != OrderStatus.WAITING_PAYMENT) {
            throw new BusinessException("Esse pedido já foi pago");
        }

        this.status = OrderStatus.RECEIVED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel(){
        if(this.status == OrderStatus.FINISHED) {
            throw new BusinessException("Esse pedido já foi finalizado");
        }

        this.status = OrderStatus.CANCELED;
        this.updatedAt = LocalDateTime.now();
    }

    public List<OrderItem> getItens() {
        return itens;
    }

    public BigDecimal getTotal(){
        return this.itens.stream().map(OrderItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void assignOrderIdToItems() {
        itens.forEach(item -> item.setOrderId(id));
    }

    public Duration timeElapsedSinceLastStatus(){
        return Duration.between(LocalDateTime.now(), this.updatedAt);
    }

    public Duration totalTimeElapsed(){
        return Duration.between(LocalDateTime.now(), this.createdAt);
    }
}