package com.bytes.service.order.mapper;

import com.bytes.service.order.adapters.outbound.persistence.entities.OrderEntity;
import com.bytes.service.order.adapters.outbound.persistence.entities.OrderItemEntity;
import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderItem;
import com.bytes.service.order.domain.models.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private OrderMappper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = Mappers.getMapper(OrderMappper.class);
    }

    @Test
    @DisplayName("Should map Order domain model to OrderEntity")
    void shouldMapOrderToOrderEntity() {
        List<OrderItem> orderItems = Arrays.asList(
            new OrderItem("Hambúrguer", "http://example.com/burger.jpg", new BigDecimal("15.99"), "LANCHE", "Delicioso hambúrguer", 2, "Sem cebola", 1L),
            new OrderItem("Refrigerante", "http://example.com/drink.jpg", new BigDecimal("5.99"), "BEBIDA", "Refrigerante gelado", 1, "Com gelo", 2L)
        );

        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(1L, now, now, OrderStatus.RECEIVED, 3L, 4L, orderItems);

        OrderEntity entity = orderMapper.toOrderEntity(order);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertEquals(OrderStatus.RECEIVED, entity.getStatus());
        assertEquals(3L, entity.getModifyById());
        assertEquals(4L, entity.getClientId());
        assertEquals(2, entity.getItens().size());

        OrderItemEntity firstItem = entity.getItens().get(0);
        assertEquals("Hambúrguer", firstItem.getName());
        assertEquals("http://example.com/burger.jpg", firstItem.getImgUrl());
        assertEquals(new BigDecimal("15.99"), firstItem.getUnitPrice());
        assertEquals("LANCHE", firstItem.getCategory());
        assertEquals("Delicioso hambúrguer", firstItem.getDescription());
        assertEquals(2, firstItem.getQuantity());
        assertEquals("Sem cebola", firstItem.getObservation());
        assertEquals(1L, firstItem.getOriginalProductId());
    }

    @Test
    @DisplayName("Should map OrderEntity to Order domain model")
    void shouldMapOrderEntityToOrder() {
        OrderItemEntity itemEntity1 = new OrderItemEntity();
        itemEntity1.setId(1L);
        itemEntity1.setName("Pizza");
        itemEntity1.setImgUrl("http://example.com/pizza.jpg");
        itemEntity1.setUnitPrice(new BigDecimal("25.99"));
        itemEntity1.setCategory("LANCHE");
        itemEntity1.setDescription("Deliciosa pizza");
        itemEntity1.setQuantity(1);
        itemEntity1.setObservation("Massa fina");
        itemEntity1.setOriginalProductId(3L);
        itemEntity1.setOrderId(2L);

        OrderItemEntity itemEntity2 = new OrderItemEntity();
        itemEntity2.setId(2L);
        itemEntity2.setName("Suco");
        itemEntity2.setImgUrl("http://example.com/juice.jpg");
        itemEntity2.setUnitPrice(new BigDecimal("7.99"));
        itemEntity2.setCategory("BEBIDA");
        itemEntity2.setDescription("Suco natural");
        itemEntity2.setQuantity(2);
        itemEntity2.setObservation("Sem açúcar");
        itemEntity2.setOriginalProductId(4L);
        itemEntity2.setOrderId(2L);

        OrderEntity entity = new OrderEntity();
        entity.setId(2L);
        entity.setCreatedAt(LocalDateTime.of(2023, 1, 1, 12, 0));
        entity.setUpdatedAt(LocalDateTime.of(2023, 1, 1, 12, 30));
        entity.setStatus(OrderStatus.PREPARING);
        entity.setModifyById(5L);
        entity.setClientId(6L);
        entity.setItens(Arrays.asList(itemEntity1, itemEntity2));

        Order order = orderMapper.toOrder(entity);

        assertNotNull(order);
        assertEquals(2L, order.getId());
        assertEquals(LocalDateTime.of(2023, 1, 1, 12, 0), order.getCreatedAt());
        assertEquals(LocalDateTime.of(2023, 1, 1, 12, 30), order.getUpdatedAt());
        assertEquals(OrderStatus.PREPARING, order.getStatus());
        assertEquals(5L, order.getModifyById());
        assertEquals(6L, order.getClientId());
        assertEquals(2, order.getItens().size());

        OrderItem firstItem = order.getItens().get(0);
        assertEquals("Pizza", firstItem.getName());
        assertEquals("http://example.com/pizza.jpg", firstItem.getImgUrl());
        assertEquals(new BigDecimal("25.99"), firstItem.getUnitPrice());
        assertEquals("LANCHE", firstItem.getCategory());
        assertEquals("Deliciosa pizza", firstItem.getDescription());
        assertEquals(1, firstItem.getQuantity());
        assertEquals("Massa fina", firstItem.getObservation());
        assertEquals(3L, firstItem.getOriginalProductId());
    }

    @Test
    @DisplayName("Should map Order with single item")
    void shouldMapOrderWithSingleItem() {
        OrderItem singleItem = new OrderItem("Café", "http://example.com/coffee.jpg", new BigDecimal("3.50"), "BEBIDA", "Café expresso", 1, "Sem açúcar", 5L);
        Order order = new Order(7L, Collections.singletonList(singleItem));

        OrderEntity entity = orderMapper.toOrderEntity(order);

        assertNotNull(entity);
        assertEquals(7L, entity.getClientId());
        assertEquals(1, entity.getItens().size());
        
        OrderItemEntity itemEntity = entity.getItens().get(0);
        assertEquals("Café", itemEntity.getName());
        assertEquals("http://example.com/coffee.jpg", itemEntity.getImgUrl());
        assertEquals(new BigDecimal("3.50"), itemEntity.getUnitPrice());
        assertEquals("BEBIDA", itemEntity.getCategory());
        assertEquals("Café expresso", itemEntity.getDescription());
        assertEquals(1, itemEntity.getQuantity());
        assertEquals("Sem açúcar", itemEntity.getObservation());
        assertEquals(5L, itemEntity.getOriginalProductId());
    }

    @Test
    @DisplayName("Should handle null Order domain model")
    void shouldHandleNullOrder() {
        OrderEntity entity = orderMapper.toOrderEntity(null);
        assertNull(entity);
    }

    @Test
    @DisplayName("Should handle null OrderEntity")
    void shouldHandleNullOrderEntity() {
        assertThrows(NullPointerException.class, () -> {
            orderMapper.toOrder(null);
        });
    }

    @Test
    @DisplayName("Should map OrderEntity with empty items list")
    void shouldMapOrderEntityWithEmptyItems() {
        OrderEntity entity = new OrderEntity();
        entity.setId(8L);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setStatus(OrderStatus.WAITING_PAYMENT);
        entity.setClientId(9L);
        entity.setItens(Collections.emptyList());

        Order order = orderMapper.toOrder(entity);

        assertNotNull(order);
        assertEquals(8L, order.getId());
        assertEquals(OrderStatus.WAITING_PAYMENT, order.getStatus());
        assertEquals(9L, order.getClientId());
        assertTrue(order.getItens().isEmpty());
    }

    @Test
    @DisplayName("Should map OrderEntity with null client id")
    void shouldMapOrderEntityWithNullClientId() {
        OrderItemEntity itemEntity = new OrderItemEntity();
        itemEntity.setName("Test Item");
        itemEntity.setUnitPrice(new BigDecimal("10.00"));
        itemEntity.setCategory("TEST");
        itemEntity.setDescription("Test description");
        itemEntity.setQuantity(1);
        itemEntity.setOriginalProductId(1L);

        OrderEntity entity = new OrderEntity();
        entity.setId(10L);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setStatus(OrderStatus.RECEIVED);
        entity.setClientId(null); // Anonymous order
        entity.setItens(Collections.singletonList(itemEntity));

        Order order = orderMapper.toOrder(entity);

        assertNotNull(order);
        assertEquals(10L, order.getId());
        assertNull(order.getClientId());
        assertEquals(OrderStatus.RECEIVED, order.getStatus());
        assertEquals(1, order.getItens().size());
    }

    @Test
    @DisplayName("Should preserve order status when mapping")
    void shouldPreserveOrderStatusWhenMapping() {
        for (OrderStatus status : OrderStatus.values()) {
            List<OrderItem> items = Collections.singletonList(
                new OrderItem("Test Item", "http://example.com/test.jpg", new BigDecimal("10.00"), "TEST", "Test description", 1, "No observation", 1L)
            );
            
            Order order = new Order(1L, LocalDateTime.now(), LocalDateTime.now(), status, 1L, 1L, items);
            
            OrderEntity entity = orderMapper.toOrderEntity(order);
            
            assertNotNull(entity);
            assertEquals(status, entity.getStatus());
            
            Order mappedBackOrder = orderMapper.toOrder(entity);
            assertEquals(status, mappedBackOrder.getStatus());
        }
    }

    @Test
    @DisplayName("Should map list of OrderItemEntity to OrderItem")
    void shouldMapOrderItemEntityListToOrderItemList() {
        OrderItemEntity itemEntity1 = new OrderItemEntity();
        itemEntity1.setId(1L);
        itemEntity1.setName("Item 1");
        itemEntity1.setImgUrl("http://example.com/item1.jpg");
        itemEntity1.setUnitPrice(new BigDecimal("12.50"));
        itemEntity1.setCategory("CATEGORY1");
        itemEntity1.setDescription("Description 1");
        itemEntity1.setQuantity(2);
        itemEntity1.setObservation("Observation 1");
        itemEntity1.setOriginalProductId(1L);

        OrderItemEntity itemEntity2 = new OrderItemEntity();
        itemEntity2.setId(2L);
        itemEntity2.setName("Item 2");
        itemEntity2.setImgUrl("http://example.com/item2.jpg");
        itemEntity2.setUnitPrice(new BigDecimal("8.75"));
        itemEntity2.setCategory("CATEGORY2");
        itemEntity2.setDescription("Description 2");
        itemEntity2.setQuantity(1);
        itemEntity2.setObservation("Observation 2");
        itemEntity2.setOriginalProductId(2L);

        List<OrderItemEntity> itemEntities = Arrays.asList(itemEntity1, itemEntity2);

        List<OrderItem> orderItems = orderMapper.toOrderItems(itemEntities);

        assertNotNull(orderItems);
        assertEquals(2, orderItems.size());

        OrderItem item1 = orderItems.get(0);
        assertEquals("Item 1", item1.getName());
        assertEquals(new BigDecimal("12.50"), item1.getUnitPrice());
        assertEquals(2, item1.getQuantity());

        OrderItem item2 = orderItems.get(1);
        assertEquals("Item 2", item2.getName());
        assertEquals(new BigDecimal("8.75"), item2.getUnitPrice());
        assertEquals(1, item2.getQuantity());
    }
}