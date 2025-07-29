package com.bytes.service.order.adapters.inbound.rest;

import com.bytes.service.order.adapters.inbound.dtos.OrderResponseDTO;
import com.bytes.service.order.adapters.inbound.dtos.UpdateOrderReq;
import com.bytes.service.order.application.OrderService;
import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.OrderStatus;
import com.bytes.service.order.domain.models.dtos.CreateOrderDTO;
import com.bytes.service.order.infra.config.ExceptionHandlerController;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    
    @Mock
    private OrderService orderService;
    
    @Mock
    private Order mockOrder;
    
    @Mock
    private MessageSource messageSource;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private OrderController orderController;
    
    @BeforeEach
    void setUp() {
        orderController = new OrderController(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
            .setControllerAdvice(new ExceptionHandlerController(messageSource))
            .build();
        objectMapper = new ObjectMapper();
    }
    
    @Test
    @DisplayName("Should create order successfully")
    void shouldCreateOrderSuccessfully() throws Exception {
        CreateOrderDTO createOrderDTO = mock(CreateOrderDTO.class);
        when(orderService.createOrder(any(CreateOrderDTO.class))).thenReturn(mockOrder);
        when(mockOrder.getId()).thenReturn(1L);
        
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        
        verify(orderService).createOrder(any(CreateOrderDTO.class));
    }
    
    @Test
    @DisplayName("Should update order status successfully")
    void shouldUpdateOrderStatusSuccessfully() throws Exception {
        UpdateOrderReq updateOrderReq = new UpdateOrderReq(1L, OrderStatus.PREPARING);
        
        mockMvc.perform(put("/order/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateOrderReq))
                .requestAttr("user_id", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("PREPARING"));
        
        verify(orderService).updateStatus(1L, OrderStatus.PREPARING, 2L);
    }
    
    @Test
    @DisplayName("Should cancel order successfully")
    void shouldCancelOrderSuccessfully() throws Exception {
        mockMvc.perform(put("/order/1")
                .requestAttr("user_id", 2L))
                .andExpect(status().isOk());
        
        verify(orderService).cancelOrder(1L, 2L);
    }
    
    @Test
    @DisplayName("Should find orders by status successfully")
    void shouldFindOrdersByStatusSuccessfully() throws Exception {
        List<Order> orders = Arrays.asList(mockOrder);
        when(orderService.findOrderByStatus(OrderStatus.RECEIVED)).thenReturn(orders);
        when(mockOrder.timeElapsedSinceLastStatus()).thenReturn(Duration.ofMinutes(5));
        when(mockOrder.totalTimeElapsed()).thenReturn(Duration.ofMinutes(10));
        
        mockMvc.perform(get("/order/status/RECEIVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
        
        verify(orderService).findOrderByStatus(OrderStatus.RECEIVED);
    }
    
    @Test
    @DisplayName("Should get order by id successfully")
    void shouldGetOrderByIdSuccessfully() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(mockOrder);
        when(mockOrder.timeElapsedSinceLastStatus()).thenReturn(Duration.ofMinutes(5));
        when(mockOrder.totalTimeElapsed()).thenReturn(Duration.ofMinutes(10));
        when(mockOrder.getId()).thenReturn(1L);
        
        mockMvc.perform(get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.id").value(1L))
                .andExpect(jsonPath("$.timeElapsedSinceLastStatus").value(300000))
                .andExpect(jsonPath("$.totalTimeElapsed").value(600000));
        
        verify(orderService).getOrderById(1L);
    }
    
    @Test
    @DisplayName("Should handle invalid order status")
    void shouldHandleInvalidOrderStatus() throws Exception {
        mockMvc.perform(get("/order/status/INVALID_STATUS"))
                .andExpect(status().isUnprocessableEntity());
    }
    
    @Test
    @DisplayName("Should handle missing user_id attribute for update status")
    void shouldHandleMissingUserIdForUpdateStatus() throws Exception {
        UpdateOrderReq updateOrderReq = new UpdateOrderReq(1L, OrderStatus.PREPARING);
        
        mockMvc.perform(put("/order/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateOrderReq)))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    @DisplayName("Should handle missing user_id attribute for cancel order")
    void shouldHandleMissingUserIdForCancelOrder() throws Exception {
        mockMvc.perform(put("/order/1"))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    @DisplayName("Should handle service exception for create order")
    void shouldHandleServiceExceptionForCreateOrder() throws Exception {
        CreateOrderDTO createOrderDTO = mock(CreateOrderDTO.class);
        when(orderService.createOrder(any(CreateOrderDTO.class)))
            .thenThrow(new RuntimeException("Service error"));
        
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderDTO)))
                .andExpect(status().isInternalServerError());
        
        verify(orderService).createOrder(any(CreateOrderDTO.class));
    }
    
    @Test
    @DisplayName("Should handle service exception for get order by id")
    void shouldHandleServiceExceptionForGetOrderById() throws Exception {
        when(orderService.getOrderById(999L))
            .thenThrow(new RuntimeException("Order not found"));
        
        mockMvc.perform(get("/order/999"))
                .andExpect(status().isInternalServerError());
        
        verify(orderService).getOrderById(999L);
    }
}