package com.bytes.service.order.application;

import com.bytes.service.order.application.useCases.GetOrdersByStatusUseCase;
import com.bytes.service.order.application.useCases.order.*;
import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderStatus;
import com.bytes.service.order.domain.models.dtos.CreateOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    private CreateOrderUseCase createOrderUseCase;
    
    @Mock
    private GetOrderByIdUseCase getOrderByIdUseCase;
    
    @Mock
    private CancelOrderUseCase cancelOrderUseCase;
    
    @Mock
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;
    
    @Mock
    private GetOrdersByStatusUseCase getOrdersByStatusUseCase;
    
    private OrderService orderService;
    
    @BeforeEach
    void setUp() {
        orderService = new OrderService(
            createOrderUseCase,
            getOrderByIdUseCase,
            cancelOrderUseCase,
            updateOrderStatusUseCase,
            getOrdersByStatusUseCase
        );
    }
    
    @Test
    @DisplayName("Should create order successfully")
    void shouldCreateOrderSuccessfully() {
        CreateOrderDTO createOrderDTO = mock(CreateOrderDTO.class);
        Order expectedOrder = mock(Order.class);
        
        when(createOrderUseCase.execute(createOrderDTO)).thenReturn(expectedOrder);
        
        Order result = orderService.createOrder(createOrderDTO);
        
        assertEquals(expectedOrder, result);
        verify(createOrderUseCase).execute(createOrderDTO);
    }
    
    @Test
    @DisplayName("Should get order by id successfully")
    void shouldGetOrderByIdSuccessfully() {
        Long orderId = 1L;
        Order expectedOrder = mock(Order.class);
        
        when(getOrderByIdUseCase.execute(orderId)).thenReturn(expectedOrder);
        
        Order result = orderService.getOrderById(orderId);
        
        assertEquals(expectedOrder, result);
        verify(getOrderByIdUseCase).execute(orderId);
    }
    
    @Test
    @DisplayName("Should cancel order successfully")
    void shouldCancelOrderSuccessfully() {
        Long orderId = 1L;
        Long modifyById = 2L;
        
        orderService.cancelOrder(orderId, modifyById);
        
        verify(cancelOrderUseCase).execute(orderId);
    }
    
    @Test
    @DisplayName("Should update order status successfully")
    void shouldUpdateOrderStatusSuccessfully() {
        Long orderId = 1L;
        OrderStatus status = OrderStatus.PREPARING;
        Long modifyById = 2L;
        
        orderService.updateStatus(orderId, status, modifyById);
        
        verify(updateOrderStatusUseCase).execute(orderId, status, modifyById);
    }
    
    @Test
    @DisplayName("Should find orders by status successfully")
    void shouldFindOrdersByStatusSuccessfully() {
        OrderStatus status = OrderStatus.RECEIVED;
        List<Order> expectedOrders = Arrays.asList(mock(Order.class), mock(Order.class));
        
        when(getOrdersByStatusUseCase.execute(status)).thenReturn(expectedOrders);
        
        List<Order> result = orderService.findOrderByStatus(status);
        
        assertEquals(expectedOrders, result);
        verify(getOrdersByStatusUseCase).execute(status);
    }
    
    @Test
    @DisplayName("Should handle null create order DTO")
    void shouldHandleNullCreateOrderDTO() {
        when(createOrderUseCase.execute(null)).thenThrow(new IllegalArgumentException("CreateOrderDTO cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(null);
        });
        
        verify(createOrderUseCase).execute(null);
    }
    
    @Test
    @DisplayName("Should handle null order id for get order")
    void shouldHandleNullOrderIdForGetOrder() {
        when(getOrderByIdUseCase.execute(null)).thenThrow(new IllegalArgumentException("Order ID cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.getOrderById(null);
        });
        
        verify(getOrderByIdUseCase).execute(null);
    }
    
    @Test
    @DisplayName("Should handle null order id for cancel order")
    void shouldHandleNullOrderIdForCancelOrder() {
        doThrow(new IllegalArgumentException("Order ID cannot be null"))
            .when(cancelOrderUseCase).execute(null);
        
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.cancelOrder(null, 1L);
        });
        
        verify(cancelOrderUseCase).execute(null);
    }
    
    @Test
    @DisplayName("Should handle null status for find orders by status")
    void shouldHandleNullStatusForFindOrdersByStatus() {
        when(getOrdersByStatusUseCase.execute(null)).thenThrow(new IllegalArgumentException("Status cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.findOrderByStatus(null);
        });
        
        verify(getOrdersByStatusUseCase).execute(null);
    }
}