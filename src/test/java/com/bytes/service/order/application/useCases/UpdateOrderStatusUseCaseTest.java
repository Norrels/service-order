package com.bytes.service.order.application.useCases;

import com.bytes.service.order.application.useCases.order.UpdateOrderStatusUseCase;
import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderStatus;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseTest {
    
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    
    @Mock
    private Order order;
    
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;
    
    @BeforeEach
    void setUp() {
        updateOrderStatusUseCase = new UpdateOrderStatusUseCase(orderRepositoryPort);
    }
    
    @Test
    @DisplayName("Should update order status successfully")
    void shouldUpdateOrderStatusSuccessfully() {
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.PREPARING;
        Long modifyById = 2L;
        
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
        when(order.getStatus()).thenReturn(OrderStatus.RECEIVED);
        
        updateOrderStatusUseCase.execute(orderId, newStatus, modifyById);
        
        verify(orderRepositoryPort).findById(orderId);
        verify(order).updateStatus(newStatus, modifyById);
        verify(orderRepositoryPort).save(order);
    }
    
    @Test
    @DisplayName("Should not update when status is the same")
    void shouldNotUpdateWhenStatusIsTheSame() {
        Long orderId = 1L;
        OrderStatus currentStatus = OrderStatus.PREPARING;
        Long modifyById = 2L;
        
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
        when(order.getStatus()).thenReturn(currentStatus);
        
        updateOrderStatusUseCase.execute(orderId, currentStatus, modifyById);
        
        verify(orderRepositoryPort).findById(orderId);
        verify(order, never()).updateStatus(any(), any());
        verify(orderRepositoryPort, never()).save(any());
    }
    
    @Test
    @DisplayName("Should throw exception when order not found")
    void shouldThrowExceptionWhenOrderNotFound() {
        Long orderId = 999L;
        OrderStatus newStatus = OrderStatus.PREPARING;
        Long modifyById = 2L;
        
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.empty());
        
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            updateOrderStatusUseCase.execute(orderId, newStatus, modifyById);
        });
        
        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(orderRepositoryPort).findById(orderId);
        verify(orderRepositoryPort, never()).save(any());
    }
    
    @Test
    @DisplayName("Should handle null order id")
    void shouldHandleNullOrderId() {
        OrderStatus newStatus = OrderStatus.PREPARING;
        Long modifyById = 2L;
        
        when(orderRepositoryPort.findById(null)).thenReturn(Optional.empty());
        
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            updateOrderStatusUseCase.execute(null, newStatus, modifyById);
        });
        
        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(orderRepositoryPort).findById(null);
    }
    
    @Test
    @DisplayName("Should propagate business exception from order update")
    void shouldPropagateBussinessExceptionFromOrderUpdate() {
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.WAITING_PAYMENT;
        Long modifyById = 2L;
        
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
        when(order.getStatus()).thenReturn(OrderStatus.RECEIVED);
        doThrow(new RuntimeException("Operação invalída")).when(order).updateStatus(newStatus, modifyById);
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateOrderStatusUseCase.execute(orderId, newStatus, modifyById);
        });
        
        assertEquals("Operação invalída", exception.getMessage());
        verify(orderRepositoryPort).findById(orderId);
        verify(order).updateStatus(newStatus, modifyById);
        verify(orderRepositoryPort, never()).save(any());
    }
    
    @Test
    @DisplayName("Should handle repository save failure")
    void shouldHandleRepositorySaveFailure() {
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.PREPARING;
        Long modifyById = 2L;
        
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
        when(order.getStatus()).thenReturn(OrderStatus.RECEIVED);
        doThrow(new RuntimeException("Database error")).when(orderRepositoryPort).save(order);
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateOrderStatusUseCase.execute(orderId, newStatus, modifyById);
        });
        
        assertEquals("Database error", exception.getMessage());
        verify(orderRepositoryPort).findById(orderId);
        verify(order).updateStatus(newStatus, modifyById);
        verify(orderRepositoryPort).save(order);
    }
}