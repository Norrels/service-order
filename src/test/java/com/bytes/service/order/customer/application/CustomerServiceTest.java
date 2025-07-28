package com.bytes.service.order.customer.application;

import com.bytes.service.order.application.CustomerService;
import com.bytes.service.order.application.useCases.CreateCustomerUseCase;
import com.bytes.service.order.application.useCases.DeleteCustomerUseCase;
import com.bytes.service.order.application.useCases.FindCustomerByCPFUseCase;
import com.bytes.service.order.application.useCases.UpdateCustomerUseCase;
import com.bytes.service.order.domain.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CreateCustomerUseCase createCustomerUseCase;
    @Mock
    private DeleteCustomerUseCase deleteCustomerUseCase;
    @Mock
    private UpdateCustomerUseCase updateCustomerUseCase;
    @Mock
    private FindCustomerByCPFUseCase findCustomerByCPFUseCase;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer() {
        Customer customerDTO = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(createCustomerUseCase.execute(any(Customer.class))).thenReturn(customerDTO);

        Customer createdCustomer = customerService.create(customerDTO);

        assertNotNull(createdCustomer);
        assertEquals("12345678910", createdCustomer.getCpf());
        verify(createCustomerUseCase, times(1)).execute(any(Customer.class));
    }

    @Test
    void deleteCustomer() {
        doNothing().when(deleteCustomerUseCase).execute(anyLong());

        customerService.deleteBy(1L);

        verify(deleteCustomerUseCase, times(1)).execute(anyLong());
    }

    @Test
    void updateCustomer() {
        Customer customerDTO = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(updateCustomerUseCase.execute(anyLong(), any(Customer.class))).thenReturn(customerDTO);

        Customer updatedCustomer = customerService.update(1L, customerDTO);

        assertNotNull(updatedCustomer);
        assertEquals("12345678910", updatedCustomer.getCpf());
        verify(updateCustomerUseCase, times(1)).execute(anyLong(), any(Customer.class));
    }

    @Test
    void findCustomerByCPF() {
        Customer customerDTO = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(findCustomerByCPFUseCase.execute(anyString())).thenReturn(customerDTO);

        Customer foundCustomer = customerService.findByCPF("12345678910");

        assertNotNull(foundCustomer);
        assertEquals("12345678910", foundCustomer.getCpf());
        verify(findCustomerByCPFUseCase, times(1)).execute(anyString());
    }
}