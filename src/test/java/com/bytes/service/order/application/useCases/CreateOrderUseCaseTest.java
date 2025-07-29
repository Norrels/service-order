package com.bytes.service.order.application.useCases;

import com.bytes.service.order.application.useCases.order.CreateOrderUseCase;
import com.bytes.service.order.domain.models.Order;
import com.bytes.service.order.domain.models.ProductCategory;
import com.bytes.service.order.domain.models.dtos.CreateOrderDTO;
import com.bytes.service.order.domain.models.dtos.OrderItemDTO;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.domain.models.Customer;
import java.util.Optional;
import com.bytes.service.order.domain.outbound.ProductRepositoryPort;
import com.bytes.service.order.domain.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {
    
    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    
    @Mock
    private ProductRepositoryPort productRepositoryPort;
    
    private CreateOrderUseCase createOrderUseCase;
    
    @BeforeEach
    void setUp() {
        createOrderUseCase = new CreateOrderUseCase(
            orderRepositoryPort,
            customerRepositoryPort,
            productRepositoryPort
        );
    }
    
    @Test
    @DisplayName("Should create order successfully with client")
    void shouldCreateOrderSuccessfullyWithClient() {
        Long clientId = 1L;
        OrderItemDTO orderItemDTO = new OrderItemDTO(1L, 2);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(clientId, List.of(orderItemDTO));
        
        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Hambúrguer");
        when(product.getImgUrl()).thenReturn("http://example.com/burger.jpg");
        when(product.getPrice()).thenReturn(new BigDecimal("15.99"));
        when(product.getCategory()).thenReturn(ProductCategory.LANCHE);
        when(product.getDescription()).thenReturn("Delicioso hambúrguer");
        when(product.getObservation()).thenReturn("Sem cebola");
        when(product.getId()).thenReturn(1L);
        
        Order savedOrder = mock(Order.class);
        Order finalOrder = mock(Order.class);
        
        when(customerRepositoryPort.findById(clientId)).thenReturn(Optional.of(mock(Customer.class)));
        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(savedOrder).thenReturn(finalOrder);
        
        Order result = createOrderUseCase.execute(createOrderDTO);
        
        assertEquals(finalOrder, result);
        verify(customerRepositoryPort).findById(clientId);
        verify(productRepositoryPort).findById(1L);
        verify(orderRepositoryPort, times(2)).save(any(Order.class));
        verify(savedOrder).assignOrderIdToItems();
    }
    
    @Test
    @DisplayName("Should create order successfully without client")
    void shouldCreateOrderSuccessfullyWithoutClient() {
        OrderItemDTO orderItemDTO = new OrderItemDTO(1L, 2);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(null, List.of(orderItemDTO));
        
        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Hambúrguer");
        when(product.getImgUrl()).thenReturn("http://example.com/burger.jpg");
        when(product.getPrice()).thenReturn(new BigDecimal("15.99"));
        when(product.getCategory()).thenReturn(ProductCategory.LANCHE);
        when(product.getDescription()).thenReturn("Delicioso hambúrguer");
        when(product.getObservation()).thenReturn("Sem cebola");
        when(product.getId()).thenReturn(1L);
        
        Order savedOrder = mock(Order.class);
        Order finalOrder = mock(Order.class);
        
        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(savedOrder).thenReturn(finalOrder);
        
        Order result = createOrderUseCase.execute(createOrderDTO);
        
        assertEquals(finalOrder, result);
        verify(customerRepositoryPort, never()).findById(any());
        verify(productRepositoryPort).findById(1L);
        verify(orderRepositoryPort, times(2)).save(any(Order.class));
        verify(savedOrder).assignOrderIdToItems();
    }
    
    @Test
    @DisplayName("Should create order with multiple items")
    void shouldCreateOrderWithMultipleItems() {
        Long clientId = 1L;
        OrderItemDTO orderItem1 = new OrderItemDTO(1L, 2);
        OrderItemDTO orderItem2 = new OrderItemDTO(2L, 1);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(clientId, List.of(orderItem1, orderItem2));
        
        Product product1 = mock(Product.class);
        when(product1.getName()).thenReturn("Hambúrguer");
        when(product1.getImgUrl()).thenReturn("http://example.com/burger.jpg");
        when(product1.getPrice()).thenReturn(new BigDecimal("15.99"));
        when(product1.getCategory()).thenReturn(ProductCategory.LANCHE);
        when(product1.getDescription()).thenReturn("Delicioso hambúrguer");
        when(product1.getObservation()).thenReturn("Sem cebola");
        when(product1.getId()).thenReturn(1L);
        
        Product product2 = mock(Product.class);
        when(product2.getName()).thenReturn("Refrigerante");
        when(product2.getImgUrl()).thenReturn("http://example.com/drink.jpg");
        when(product2.getPrice()).thenReturn(new BigDecimal("5.99"));
        when(product2.getCategory()).thenReturn(ProductCategory.BEBIDA);
        when(product2.getDescription()).thenReturn("Refrigerante gelado");
        when(product2.getObservation()).thenReturn("Com gelo");
        when(product2.getId()).thenReturn(2L);
        
        Order savedOrder = mock(Order.class);
        Order finalOrder = mock(Order.class);
        
        when(customerRepositoryPort.findById(clientId)).thenReturn(Optional.of(mock(Customer.class)));
        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepositoryPort.findById(2L)).thenReturn(Optional.of(product2));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(savedOrder).thenReturn(finalOrder);
        
        Order result = createOrderUseCase.execute(createOrderDTO);
        
        assertEquals(finalOrder, result);
        verify(customerRepositoryPort).findById(clientId);
        verify(productRepositoryPort).findById(1L);
        verify(productRepositoryPort).findById(2L);
        verify(orderRepositoryPort, times(2)).save(any(Order.class));
        verify(savedOrder).assignOrderIdToItems();
    }
    
    @Test
    @DisplayName("Should throw exception when client does not exist")
    void shouldThrowExceptionWhenClientDoesNotExist() {
        Long clientId = 1L;
        OrderItemDTO orderItemDTO = new OrderItemDTO(1L, 2);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(clientId, List.of(orderItemDTO));
        
        when(customerRepositoryPort.findById(clientId)).thenReturn(Optional.empty());
        
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            createOrderUseCase.execute(createOrderDTO);
        });
        
        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(customerRepositoryPort).findById(clientId);
        verify(productRepositoryPort, never()).findById(any());
        verify(orderRepositoryPort, never()).save(any());
    }
    
    @Test
    @DisplayName("Should throw exception when product does not exist")
    void shouldThrowExceptionWhenProductDoesNotExist() {
        Long clientId = 1L;
        OrderItemDTO orderItemDTO = new OrderItemDTO(999L, 2);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(clientId, List.of(orderItemDTO));
        
        when(customerRepositoryPort.findById(clientId)).thenReturn(Optional.of(mock(Customer.class)));
        when(productRepositoryPort.findById(999L)).thenReturn(Optional.empty());
        
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            createOrderUseCase.execute(createOrderDTO);
        });
        
        assertEquals("Produto não encontrado com ID: 999", exception.getMessage());
        verify(customerRepositoryPort).findById(clientId);
        verify(productRepositoryPort).findById(999L);
        verify(orderRepositoryPort, never()).save(any());
    }
    
    @Test
    @DisplayName("Should validate order creation with correct product data")
    void shouldValidateOrderCreationWithCorrectProductData() {
        OrderItemDTO orderItemDTO = new OrderItemDTO(1L, 3);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(null, List.of(orderItemDTO));
        
        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Pizza");
        when(product.getImgUrl()).thenReturn("http://example.com/pizza.jpg");
        when(product.getPrice()).thenReturn(new BigDecimal("25.99"));
        when(product.getCategory()).thenReturn(ProductCategory.LANCHE);
        when(product.getDescription()).thenReturn("Deliciosa pizza");
        when(product.getObservation()).thenReturn("Massa fina");
        when(product.getId()).thenReturn(1L);
        
        Order savedOrder = mock(Order.class);
        Order finalOrder = mock(Order.class);
        
        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(savedOrder).thenReturn(finalOrder);
        
        createOrderUseCase.execute(createOrderDTO);
        
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepositoryPort, times(2)).save(orderCaptor.capture());
        
        Order capturedOrder = orderCaptor.getAllValues().get(0);
        assertNotNull(capturedOrder);
        assertEquals(1, capturedOrder.getItens().size());
        
        var orderItem = capturedOrder.getItens().get(0);
        assertEquals("Pizza", orderItem.getName());
        assertEquals("http://example.com/pizza.jpg", orderItem.getImgUrl());
        assertEquals(new BigDecimal("25.99"), orderItem.getUnitPrice());
        assertEquals("LANCHE", orderItem.getCategory());
        assertEquals("Deliciosa pizza", orderItem.getDescription());
        assertEquals(3, orderItem.getQuantity());
        assertEquals("Massa fina", orderItem.getObservation());
        assertEquals(1L, orderItem.getOriginalProductId());
    }
    
    @Test
    @DisplayName("Should handle repository save failure")
    void shouldHandleRepositorySaveFailure() {
        OrderItemDTO orderItemDTO = new OrderItemDTO(1L, 2);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(null, List.of(orderItemDTO));
        
        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Hambúrguer");
        when(product.getImgUrl()).thenReturn("http://example.com/burger.jpg");
        when(product.getPrice()).thenReturn(new BigDecimal("15.99"));
        when(product.getCategory()).thenReturn(ProductCategory.LANCHE);
        when(product.getDescription()).thenReturn("Delicioso hambúrguer");
        when(product.getObservation()).thenReturn("Sem cebola");
        when(product.getId()).thenReturn(1L);
        
        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepositoryPort.save(any(Order.class))).thenThrow(new RuntimeException("Database error"));
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createOrderUseCase.execute(createOrderDTO);
        });
        
        assertEquals("Database error", exception.getMessage());
        verify(productRepositoryPort).findById(1L);
        verify(orderRepositoryPort).save(any(Order.class));
    }
}