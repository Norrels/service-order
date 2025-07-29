package com.bytes.service.order.customer.application.useCases;

import com.bytes.service.order.application.useCases.customer.DeleteCustomerUseCase;
import com.bytes.service.order.domain.models.Customer;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteCustomerUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @InjectMocks
    private DeleteCustomerUseCase deleteCustomerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteCustomer() {
        when(customerRepository.findById(12L)).thenReturn(Optional.of(new Customer(12L, "12345678910", "test@gmail.com", "Test User", "123456789")));

        assertDoesNotThrow(() -> deleteCustomerUseCase.execute(12L));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        doThrow(new ResourceNotFoundException("Cliente não encontrado")).when(customerRepository).delete(1L);

        assertThrows(ResourceNotFoundException.class, () -> deleteCustomerUseCase.execute(1L));
    }
}