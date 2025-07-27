package com.bytes.bytes.contexts.customer.application.useCases;

import com.bytes.bytes.contexts.customer.domain.models.Customer;
import com.bytes.bytes.contexts.customer.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.bytes.exceptions.BusinessException;
import com.bytes.bytes.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindCustomerByCPFUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @InjectMocks
    private FindCustomerByCPFUseCase findCustomerByCPFUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindCustomerByCPF() {
        Customer customer = new Customer(1L, "12345678910", "test@example.com", "Test User", "123456789");
        when(customerRepository.findByCPF("12345678910")).thenReturn(Optional.of(customer));

        Customer foundCustomer = findCustomerByCPFUseCase.execute("12345678910");
        assertNotNull(foundCustomer);
    }

    @Test
    void shouldThrowsExceptionWhenCustomerNotFound() {
        when(customerRepository.findByCPF("12345678910")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> findCustomerByCPFUseCase.execute("12345678910"));
    }


    @Test
    void shouldThrowsExceptionWhenCPFIsNull() {
        when(customerRepository.findByCPF("")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> findCustomerByCPFUseCase.execute(""));
    }

    @Test
    void shouldThrowsExceptionWhenCPFLengthIsLessThanEleven() {
        assertThrows(BusinessException.class, () -> findCustomerByCPFUseCase.execute("1223"));
    }
}