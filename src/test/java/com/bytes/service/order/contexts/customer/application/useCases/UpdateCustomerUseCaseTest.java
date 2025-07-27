package com.bytes.service.order.contexts.customer.application.useCases;

import com.bytes.service.order.contexts.customer.domain.models.Customer;
import com.bytes.service.order.contexts.customer.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UpdateCustomerUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @InjectMocks
    private UpdateCustomerUseCase updateCustomerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateCustomer() {
        Customer customer = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer updatedCustomer = updateCustomerUseCase.execute(1L, customer);
        assertEquals("12345678910", updatedCustomer.getCpf());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        Customer customer = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> updateCustomerUseCase.execute(1L, customer));
    }
}