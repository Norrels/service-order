package com.bytes.service.order.adapters.outbound.persistence.repository;

import com.bytes.service.order.adapters.outbound.persistence.entities.OrderEntity;
import com.bytes.service.order.adapters.outbound.persistence.repositories.OrderRepository;
import com.bytes.service.order.adapters.outbound.persistence.repositories.OrderRepositoryAdapeter;
import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderStatus;
import com.bytes.service.order.mapper.OrderMappper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryAdapeterTest {
    
    @Mock
    private OrderRepository orderRepository;
    
    @Mock
    private OrderMappper orderMapper;
    
    @Mock
    private Order mockOrder;
    
    @Mock
    private OrderEntity mockOrderEntity;
    
    private OrderRepositoryAdapeter orderRepositoryAdapter;
    
    @BeforeEach
    void setUp() {
        orderRepositoryAdapter = new OrderRepositoryAdapeter(orderRepository, orderMapper);
    }
    
    @Test
    @DisplayName("Should save order successfully")
    void shouldSaveOrderSuccessfully() {
        when(orderMapper.toOrderEntity(mockOrder)).thenReturn(mockOrderEntity);
        when(orderRepository.save(mockOrderEntity)).thenReturn(mockOrderEntity);
        when(orderMapper.toOrder(mockOrderEntity)).thenReturn(mockOrder);
        
        Order result = orderRepositoryAdapter.save(mockOrder);
        
        assertEquals(mockOrder, result);
        verify(orderMapper).toOrderEntity(mockOrder);
        verify(orderRepository).save(mockOrderEntity);
        verify(orderMapper).toOrder(mockOrderEntity);
    }
    
    @Test
    @DisplayName("Should find order by ID successfully")
    void shouldFindOrderByIDSuccessfully() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrderEntity));
        when(orderMapper.toOrder(mockOrderEntity)).thenReturn(mockOrder);
        
        Optional<Order> result = orderRepositoryAdapter.findById(orderId);
        
        assertTrue(result.isPresent());
        assertEquals(mockOrder, result.get());
        verify(orderRepository).findById(orderId);
        verify(orderMapper).toOrder(mockOrderEntity);
    }
    
    @Test
    @DisplayName("Should return empty when order not found by ID")
    void shouldReturnEmptyWhenOrderNotFoundByID() {
        Long orderId = 999L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        
        Optional<Order> result = orderRepositoryAdapter.findById(orderId);
        
        assertFalse(result.isPresent());
        verify(orderRepository).findById(orderId);
        verify(orderMapper, never()).toOrder(any());
    }
    
    @Test
    @DisplayName("Should find orders by status successfully")
    void shouldFindOrdersByStatusSuccessfully() {
        OrderStatus status = OrderStatus.RECEIVED;
        List<OrderEntity> orderEntities = Arrays.asList(mockOrderEntity, mockOrderEntity);
        List<Order> expectedOrders = Arrays.asList(mockOrder, mockOrder);
        
        when(orderRepository.findByStatus(status)).thenReturn(orderEntities);
        when(orderMapper.toOrder(mockOrderEntity)).thenReturn(mockOrder);
        
        List<Order> result = orderRepositoryAdapter.findOrdersByStatus(status);
        
        assertEquals(2, result.size());
        assertEquals(expectedOrders, result);
        verify(orderRepository).findByStatus(status);
        verify(orderMapper, times(2)).toOrder(mockOrderEntity);
    }
    
    @Test
    @DisplayName("Should return empty list when no orders found by status")
    void shouldReturnEmptyListWhenNoOrdersFoundByStatus() {
        OrderStatus status = OrderStatus.FINISHED;
        when(orderRepository.findByStatus(status)).thenReturn(Arrays.asList());
        
        List<Order> result = orderRepositoryAdapter.findOrdersByStatus(status);
        
        assertTrue(result.isEmpty());
        verify(orderRepository).findByStatus(status);
        verify(orderMapper, never()).toOrder(any());
    }
    
    @Test
    @DisplayName("Should handle null order for save")
    void shouldHandleNullOrderForSave() {
        when(orderMapper.toOrderEntity(null)).thenReturn(null);
        when(orderRepository.save(null)).thenThrow(new IllegalArgumentException("Order cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            orderRepositoryAdapter.save(null);
        });
        
        verify(orderMapper).toOrderEntity(null);
        verify(orderRepository).save(null);
    }
    
    @Test
    @DisplayName("Should handle repository exception for save")
    void shouldHandleRepositoryExceptionForSave() {
        when(orderMapper.toOrderEntity(mockOrder)).thenReturn(mockOrderEntity);
        when(orderRepository.save(mockOrderEntity)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            orderRepositoryAdapter.save(mockOrder);
        });
        
        verify(orderMapper).toOrderEntity(mockOrder);
        verify(orderRepository).save(mockOrderEntity);
    }
    
    @Test
    @DisplayName("Should handle repository exception for findById")
    void shouldHandleRepositoryExceptionForFindById() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            orderRepositoryAdapter.findById(orderId);
        });
        
        verify(orderRepository).findById(orderId);
    }
    
    @Test
    @DisplayName("Should handle repository exception for findOrdersByStatus")
    void shouldHandleRepositoryExceptionForFindOrdersByStatus() {
        OrderStatus status = OrderStatus.RECEIVED;
        when(orderRepository.findByStatus(status)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            orderRepositoryAdapter.findOrdersByStatus(status);
        });
        
        verify(orderRepository).findByStatus(status);
    }
    
    @Test
    @DisplayName("Should handle mapper exception for save")
    void shouldHandleMapperExceptionForSave() {
        when(orderMapper.toOrderEntity(mockOrder)).thenThrow(new RuntimeException("Mapper error"));
        
        assertThrows(RuntimeException.class, () -> {
            orderRepositoryAdapter.save(mockOrder);
        });
        
        verify(orderMapper).toOrderEntity(mockOrder);
        verify(orderRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Should handle mapper exception for findById")
    void shouldHandleMapperExceptionForFindById() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrderEntity));
        when(orderMapper.toOrder(mockOrderEntity)).thenThrow(new RuntimeException("Mapper error"));
        
        assertThrows(RuntimeException.class, () -> {
            orderRepositoryAdapter.findById(orderId);
        });
        
        verify(orderRepository).findById(orderId);
        verify(orderMapper).toOrder(mockOrderEntity);
    }
    
    @Test
    @DisplayName("Should handle mapper exception for findOrdersByStatus")
    void shouldHandleMapperExceptionForFindOrdersByStatus() {
        OrderStatus status = OrderStatus.RECEIVED;
        List<OrderEntity> orderEntities = Arrays.asList(mockOrderEntity);
        
        when(orderRepository.findByStatus(status)).thenReturn(orderEntities);
        when(orderMapper.toOrder(mockOrderEntity)).thenThrow(new RuntimeException("Mapper error"));
        
        assertThrows(RuntimeException.class, () -> {
            orderRepositoryAdapter.findOrdersByStatus(status);
        });
        
        verify(orderRepository).findByStatus(status);
        verify(orderMapper).toOrder(mockOrderEntity);
    }
}