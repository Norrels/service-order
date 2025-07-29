package com.bytes.service.order.mapper;

import com.bytes.service.order.adapters.outbound.persistence.entities.OrderEntity;
import com.bytes.service.order.adapters.outbound.persistence.entities.OrderItemEntity;
import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMappper {
    @Mapping(target = "itens.order", ignore = true)
    OrderEntity toOrderEntity(Order order);

    @Named("toOrder")
    @Mapping(target = "itens.order", ignore = true)
    default Order toOrder(OrderEntity order) {
        return createOrder(order);
    }

    List<OrderItem> toOrderItems(List<OrderItemEntity> items);

    default Order createOrder(OrderEntity dto) {
        return new Order(
                dto.getId(),
                dto.getCreatedAt(),
                dto.getUpdatedAt(),
                dto.getStatus(),
                dto.getModifyById(),
                dto.getClientId(),
                toOrderItems(dto.getItens())
        );
    }
}
