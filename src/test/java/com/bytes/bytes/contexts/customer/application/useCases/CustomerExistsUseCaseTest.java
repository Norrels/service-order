package com.bytes.bytes.contexts.customer.application.useCases;

import com.bytes.bytes.contexts.customer.domain.models.Customer;
import com.bytes.bytes.contexts.customer.domain.ports.outbound.CustomerRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CustomerExistsUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @InjectMocks
    private CustomerExistsUseCase customerExistsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTrueWhenCustomerExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(new Customer(12L, "12345678910", "test@gmail.com", "Test User", "123456789")));

        boolean exists = customerExistsUseCase.execute(1L);
        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenCustomerNotExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        boolean exists = customerExistsUseCase.execute(1L);
        assertFalse(exists);
    }
}