package com.bytes.service.order.adapters.inbound.rest;

import com.bytes.service.order.adapters.inbound.dtos.CustomerReq;
import com.bytes.service.order.adapters.outbound.persistence.entities.CustomerEntity;
import com.bytes.service.order.application.CustomerService;
import com.bytes.service.order.domain.models.Customer;
import com.bytes.service.order.exceptions.BusinessException;
import com.bytes.service.order.infra.config.ExceptionHandlerController;
import com.bytes.service.order.mapper.CustomerMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    
    @Mock
    private CustomerService customerService;
    
    @Mock
    private CustomerMapper customerMapper;
    
    @Mock
    private Customer mockCustomer;
    
    @Mock
    private MessageSource messageSource;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CustomerController customerController;
    
    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService, customerMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
            .setControllerAdvice(new ExceptionHandlerController(messageSource))
            .build();
        objectMapper = new ObjectMapper();
    }
    
    @Test
    @DisplayName("Should create customer successfully")
    void shouldCreateCustomerSuccessfully() throws Exception {
        CustomerReq customerReq = createValidCustomerReq();
        when(customerMapper.toCustomer(any(CustomerReq.class))).thenReturn(mockCustomer);
        when(customerService.create(mockCustomer)).thenReturn(mockCustomer);
        when(mockCustomer.getId()).thenReturn(1L);
        when(mockCustomer.getName()).thenReturn("João Silva");
        when(mockCustomer.getCpf()).thenReturn("12345678901");
        
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
        
        verify(customerMapper).toCustomer(any(CustomerReq.class));
        verify(customerService).create(mockCustomer);
    }
    
    @Test
    @DisplayName("Should find customer by CPF successfully")
    void shouldFindCustomerByCPFSuccessfully() throws Exception {
        String cpf = "12345678901";
        when(customerService.findByCPF(cpf)).thenReturn(mockCustomer);
        when(mockCustomer.getId()).thenReturn(1L);
        when(mockCustomer.getName()).thenReturn("João Silva");
        when(mockCustomer.getCpf()).thenReturn(cpf);
        
        mockMvc.perform(get("/customer/" + cpf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value(cpf));
        
        verify(customerService).findByCPF(cpf);
    }
    
    @Test
    @DisplayName("Should update customer successfully")
    void shouldUpdateCustomerSuccessfully() throws Exception {
        Long customerId = 1L;
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerId);
        customerEntity.setName("João Silva Atualizado");
        customerEntity.setCpf("12345678901");
        customerEntity.setEmail("joao.updated@email.com");
        customerEntity.setPhone("987654321");
        
        when(customerMapper.toCustomer(any(CustomerEntity.class))).thenReturn(mockCustomer);
        when(customerService.update(eq(customerId), any(Customer.class))).thenReturn(mockCustomer);
        when(mockCustomer.getId()).thenReturn(customerId);
        when(mockCustomer.getName()).thenReturn("João Silva Atualizado");
        
        mockMvc.perform(put("/customer/" + customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value("João Silva Atualizado"));
        
        verify(customerMapper).toCustomer(any(CustomerEntity.class));
        verify(customerService).update(eq(customerId), any(Customer.class));
    }
    
    @Test
    @DisplayName("Should throw exception when IDs don't match for update")
    void shouldThrowExceptionWhenIDsDontMatchForUpdate() throws Exception {
        Long urlId = 1L;
        Long bodyId = 2L;
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(bodyId);
        customerEntity.setName("João Silva");
        
        mockMvc.perform(put("/customer/" + urlId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerEntity)))
                .andExpect(status().isUnprocessableEntity());
        
        verify(customerService, never()).update(any(), any());
    }
    
    @Test
    @DisplayName("Should handle service exception for create customer")
    void shouldHandleServiceExceptionForCreateCustomer() throws Exception {
        CustomerReq customerReq = createValidCustomerReq();
        when(customerMapper.toCustomer(any(CustomerReq.class))).thenReturn(mockCustomer);
        when(customerService.create(mockCustomer)).thenThrow(new BusinessException("CPF já existe"));
        
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerReq)))
                .andExpect(status().isUnprocessableEntity());
        
        verify(customerService).create(mockCustomer);
    }
    
    @Test
    @DisplayName("Should handle service exception for find customer by CPF")
    void shouldHandleServiceExceptionForFindCustomerByCPF() throws Exception {
        String cpf = "99999999999";
        when(customerService.findByCPF(cpf)).thenThrow(new RuntimeException("Customer not found"));
        
        mockMvc.perform(get("/customer/" + cpf))
                .andExpect(status().isInternalServerError());
        
        verify(customerService).findByCPF(cpf);
    }
    
    @Test
    @DisplayName("Should handle service exception for update customer")
    void shouldHandleServiceExceptionForUpdateCustomer() throws Exception {
        Long customerId = 1L;
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerId);
        customerEntity.setName("João Silva");
        
        when(customerMapper.toCustomer(any(CustomerEntity.class))).thenReturn(mockCustomer);
        when(customerService.update(eq(customerId), any(Customer.class)))
            .thenThrow(new RuntimeException("Customer not found"));
        
        mockMvc.perform(put("/customer/" + customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerEntity)))
                .andExpect(status().isInternalServerError());
        
        verify(customerService).update(eq(customerId), any(Customer.class));
    }
    
    @Test
    @DisplayName("Should handle invalid customer request validation")
    void shouldHandleInvalidCustomerRequestValidation() throws Exception {
        CustomerReq invalidCustomerReq = new CustomerReq();
        invalidCustomerReq.setCpf("123"); // Invalid CPF length
        invalidCustomerReq.setEmail("invalid-email"); // Invalid email format
        invalidCustomerReq.setPhone("123"); // Invalid phone length
        // Missing name (required)
        
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCustomerReq)))
                .andExpect(status().isBadRequest());
        
        verify(customerService, never()).create(any());
    }
    
    @Test
    @DisplayName("Should handle malformed JSON request")
    void shouldHandleMalformedJSONRequest() throws Exception {
        String malformedJson = "{\"name\": \"João\", \"cpf\":}"; // Invalid JSON
        
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest());
        
        verify(customerService, never()).create(any());
    }
    
    private CustomerReq createValidCustomerReq() {
        CustomerReq customerReq = new CustomerReq();
        customerReq.setCpf("12345678901");
        customerReq.setEmail("joao@email.com");
        customerReq.setName("João Silva");
        customerReq.setPhone("987654321");
        return customerReq;
    }
}