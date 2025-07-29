package com.bytes.service.order.adapters.outbound.persistence.repositories;

import com.bytes.service.order.adapters.outbound.persistence.entities.CustomerEntity;
import com.bytes.service.order.domain.models.Customer;
import com.bytes.service.order.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryAdapterTest {
    
    @Mock
    private CustomerRepository customerRepository;
    
    @Mock
    private CustomerMapper customerMapper;
    
    @Mock
    private Customer mockCustomer;
    
    @Mock
    private CustomerEntity mockCustomerEntity;
    
    private CustomerRepositoryAdapter customerRepositoryAdapter;
    
    @BeforeEach
    void setUp() {
        customerRepositoryAdapter = new CustomerRepositoryAdapter(customerRepository, customerMapper);
    }
    
    @Test
    @DisplayName("Should save customer successfully")
    void shouldSaveCustomerSuccessfully() {
        when(customerMapper.toCustomerEntity(mockCustomer)).thenReturn(mockCustomerEntity);
        when(customerRepository.save(mockCustomerEntity)).thenReturn(mockCustomerEntity);
        when(customerMapper.toCustomer((CustomerEntity) mockCustomerEntity)).thenReturn(mockCustomer);
        
        Customer result = customerRepositoryAdapter.save(mockCustomer);
        
        assertEquals(mockCustomer, result);
        verify(customerMapper).toCustomerEntity(mockCustomer);
        verify(customerRepository).save(mockCustomerEntity);
        verify(customerMapper).toCustomer(mockCustomerEntity);
    }
    
    @Test
    @DisplayName("Should delete customer successfully")
    void shouldDeleteCustomerSuccessfully() {
        Long customerId = 1L;
        
        customerRepositoryAdapter.delete(customerId);
        
        verify(customerRepository).deleteById(customerId);
    }
    
    @Test
    @DisplayName("Should find customer by CPF successfully")
    void shouldFindCustomerByCPFSuccessfully() {
        String cpf = "12345678901";
        when(customerRepository.findByCpf(cpf)).thenReturn(Optional.of(mockCustomerEntity));
        when(customerMapper.toCustomer((CustomerEntity) mockCustomerEntity)).thenReturn(mockCustomer);
        
        Optional<Customer> result = customerRepositoryAdapter.findByCPF(cpf);
        
        assertTrue(result.isPresent());
        assertEquals(mockCustomer, result.get());
        verify(customerRepository).findByCpf(cpf);
        verify(customerMapper).toCustomer(mockCustomerEntity);
    }
    
    @Test
    @DisplayName("Should return empty when customer not found by CPF")
    void shouldReturnEmptyWhenCustomerNotFoundByCPF() {
        String cpf = "99999999999";
        when(customerRepository.findByCpf(cpf)).thenReturn(Optional.empty());
        
        Optional<Customer> result = customerRepositoryAdapter.findByCPF(cpf);
        
        assertFalse(result.isPresent());
        verify(customerRepository).findByCpf(cpf);
        verify(customerMapper, never()).toCustomer(any(CustomerEntity.class));
    }
    
    @Test
    @DisplayName("Should find customer by ID successfully")
    void shouldFindCustomerByIDSuccessfully() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomerEntity));
        when(customerMapper.toCustomer((CustomerEntity) mockCustomerEntity)).thenReturn(mockCustomer);
        
        Optional<Customer> result = customerRepositoryAdapter.findById(customerId);
        
        assertTrue(result.isPresent());
        assertEquals(mockCustomer, result.get());
        verify(customerRepository).findById(customerId);
        verify(customerMapper).toCustomer(mockCustomerEntity);
    }
    
    @Test
    @DisplayName("Should return empty when customer not found by ID")
    void shouldReturnEmptyWhenCustomerNotFoundByID() {
        Long customerId = 999L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        
        Optional<Customer> result = customerRepositoryAdapter.findById(customerId);
        
        assertFalse(result.isPresent());
        verify(customerRepository).findById(customerId);
        verify(customerMapper, never()).toCustomer(any(CustomerEntity.class));
    }
    
    @Test
    @DisplayName("Should check if email exists successfully")
    void shouldCheckIfEmailExistsSuccessfully() {
        String email = "test@email.com";
        when(customerRepository.existsByEmail(email)).thenReturn(true);
        
        boolean result = customerRepositoryAdapter.existsByEmail(email);
        
        assertTrue(result);
        verify(customerRepository).existsByEmail(email);
    }
    
    @Test
    @DisplayName("Should return false when email does not exist")
    void shouldReturnFalseWhenEmailDoesNotExist() {
        String email = "nonexistent@email.com";
        when(customerRepository.existsByEmail(email)).thenReturn(false);
        
        boolean result = customerRepositoryAdapter.existsByEmail(email);
        
        assertFalse(result);
        verify(customerRepository).existsByEmail(email);
    }
    
    @Test
    @DisplayName("Should check if CPF exists successfully")
    void shouldCheckIfCPFExistsSuccessfully() {
        String cpf = "12345678901";
        when(customerRepository.existsByCpf(cpf)).thenReturn(true);
        
        boolean result = customerRepositoryAdapter.existsByCpf(cpf);
        
        assertTrue(result);
        verify(customerRepository).existsByCpf(cpf);
    }
    
    @Test
    @DisplayName("Should return false when CPF does not exist")
    void shouldReturnFalseWhenCPFDoesNotExist() {
        String cpf = "99999999999";
        when(customerRepository.existsByCpf(cpf)).thenReturn(false);
        
        boolean result = customerRepositoryAdapter.existsByCpf(cpf);
        
        assertFalse(result);
        verify(customerRepository).existsByCpf(cpf);
    }
    
    @Test
    @DisplayName("Should handle null customer for save")
    void shouldHandleNullCustomerForSave() {
        when(customerMapper.toCustomerEntity(null)).thenReturn(null);
        when(customerRepository.save(null)).thenThrow(new IllegalArgumentException("Customer cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepositoryAdapter.save(null);
        });
        
        verify(customerMapper).toCustomerEntity(null);
        verify(customerRepository).save(null);
    }
    
    @Test
    @DisplayName("Should handle repository exception for save")
    void shouldHandleRepositoryExceptionForSave() {
        when(customerMapper.toCustomerEntity(mockCustomer)).thenReturn(mockCustomerEntity);
        when(customerRepository.save(mockCustomerEntity)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            customerRepositoryAdapter.save(mockCustomer);
        });
        
        verify(customerMapper).toCustomerEntity(mockCustomer);
        verify(customerRepository).save(mockCustomerEntity);
    }
    
    @Test
    @DisplayName("Should handle repository exception for delete")
    void shouldHandleRepositoryExceptionForDelete() {
        Long customerId = 1L;
        doThrow(new RuntimeException("Database error")).when(customerRepository).deleteById(customerId);
        
        assertThrows(RuntimeException.class, () -> {
            customerRepositoryAdapter.delete(customerId);
        });
        
        verify(customerRepository).deleteById(customerId);
    }
}