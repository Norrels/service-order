package com.bytes.bytes.contexts.order.mappers;

import com.bytes.bytes.contexts.order.adapters.outbound.persistence.entity.OrderEntity;
import com.bytes.bytes.contexts.order.adapters.outbound.persistence.entity.OrderItemEntity;
import com.bytes.bytes.contexts.order.domain.models.Order;
import com.bytes.bytes.contexts.order.domain.models.OrderItem;
import com.bytes.bytes.contexts.shared.dtos.OrderDTO;
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
    OrderDTO toOrderDTO(Order order);

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
