package com.bytes.service.order.mapper;

import com.bytes.service.order.adapters.inbound.dtos.CustomerReq;
import com.bytes.service.order.adapters.outbound.persistence.entities.CustomerEntity;
import com.bytes.service.order.domain.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerMapper = Mappers.getMapper(CustomerMapper.class);
    }

    @Test
    @DisplayName("Should map CustomerEntity to Customer domain model")
    void shouldMapCustomerEntityToCustomer() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1L);
        entity.setCpf("12345678901");
        entity.setEmail("test@example.com");
        entity.setName("João Silva");
        entity.setPhone("123456789");

        Customer customer = customerMapper.toCustomer(entity);

        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("12345678901", customer.getCpf());
        assertEquals("test@example.com", customer.getEmail());
        assertEquals("João Silva", customer.getName());
        assertEquals("123456789", customer.getPhone());
    }

    @Test
    @DisplayName("Should map CustomerReq to Customer domain model")
    void shouldMapCustomerReqToCustomer() {
        CustomerReq request = new CustomerReq();
        request.setCpf("12345678901");
        request.setEmail("test@example.com");
        request.setName("João Silva");
        request.setPhone("123456789");

        Customer customer = customerMapper.toCustomer(request);

        assertNotNull(customer);
        assertEquals("12345678901", customer.getCpf());
        assertEquals("test@example.com", customer.getEmail());
        assertEquals("João Silva", customer.getName());
        assertEquals("123456789", customer.getPhone());
    }

    @Test
    @DisplayName("Should map Customer domain model to CustomerEntity")
    void shouldMapCustomerToCustomerEntity() {
        Customer customer = new Customer(1L, "12345678901", "test@example.com", "João Silva", "123456789");

        CustomerEntity entity = customerMapper.toCustomerEntity(customer);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("12345678901", entity.getCpf());
        assertEquals("test@example.com", entity.getEmail());
        assertEquals("João Silva", entity.getName());
        assertEquals("123456789", entity.getPhone());
    }

    @Test
    @DisplayName("Should handle null CustomerEntity")
    void shouldHandleNullCustomerEntity() {
        Customer customer = customerMapper.toCustomer((CustomerEntity) null);
        assertNull(customer);
    }

    @Test
    @DisplayName("Should handle null CustomerReq")
    void shouldHandleNullCustomerReq() {
        Customer customer = customerMapper.toCustomer((CustomerReq) null);
        assertNull(customer);
    }

    @Test
    @DisplayName("Should handle null Customer domain model")
    void shouldHandleNullCustomer() {
        CustomerEntity entity = customerMapper.toCustomerEntity(null);
        assertNull(entity);
    }

    @Test
    @DisplayName("Should map CustomerEntity with partial data")
    void shouldMapCustomerEntityWithPartialData() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(2L);
        entity.setCpf("98765432100");
        entity.setEmail("partial@example.com");
        entity.setName("Maria Silva");

        Customer customer = customerMapper.toCustomer(entity);

        assertNotNull(customer);
        assertEquals(2L, customer.getId());
        assertEquals("98765432100", customer.getCpf());
        assertEquals("partial@example.com", customer.getEmail());
        assertEquals("Maria Silva", customer.getName());
        assertNull(customer.getPhone());
    }
}