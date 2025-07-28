package com.bytes.service.order.customer.application.useCases;

import com.bytes.service.order.application.useCases.CreateCustomerUseCase;
import com.bytes.service.order.domain.models.Customer;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCustomerUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @InjectMocks
    private CreateCustomerUseCase createCustomerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCustomer() {
        Customer customer = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(customerRepository.existsByCpf(anyString())).thenReturn(false);
        when(customerRepository.existsByEmail(anyString())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = createCustomerUseCase.execute(customer);

        assertNotNull(createdCustomer);
        assertEquals("12345678910", createdCustomer.getCpf());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionWhenCpfExists() {
        Customer customer = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(customerRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> createCustomerUseCase.execute(customer));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        Customer customer = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(customerRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> createCustomerUseCase.execute(customer));
        verify(customerRepository, never()).save(any(Customer.class));
    }
}