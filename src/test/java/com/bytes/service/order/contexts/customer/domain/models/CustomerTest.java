package com.bytes.service.order.contexts.customer.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Customer dummyCustomer;

    @BeforeEach
    void setUp() {
      dummyCustomer = new Customer(14L, "12345678910", "dummy@gmail.com", "Dummy", "233244222");
    }

    @Test
    @DisplayName("Should be able to update a customer")
    void update() {
        dummyCustomer.update("12345678910", "johndoe@gmail.com", "John Doe", "112");
        assertEquals(14L, dummyCustomer.getId());
    }

    @Test
    @DisplayName("Shouldn't update if customer has no name")
    void shouldNotUpdateIfCustomerHasNoName() {
        assertThrows(IllegalArgumentException.class, () -> {
            dummyCustomer.update("12345678910", "johndoe@gmail.com", "", "112");
        });
    }

    @Test
    @DisplayName("Shouldn't update if customer has no cpf")
    void shouldNotUpdateIfCustomerHasNoCPF() {
        assertThrows(IllegalArgumentException.class, () -> {
            dummyCustomer.update("", "johndoe@gmail.com", "John doe", "112");
        });
    }

    @Test
    @DisplayName("Shouldn't update if customer has no email")
    void shouldNotUpdate() {
        assertThrows(IllegalArgumentException.class, () -> {
            dummyCustomer.update("12345678910", "", "John doe", "112");
        });
    }
}