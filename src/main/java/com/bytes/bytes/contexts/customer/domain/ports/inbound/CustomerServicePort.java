package com.bytes.bytes.contexts.customer.domain.ports.inbound;

import com.bytes.bytes.contexts.customer.domain.models.Customer;

public interface CustomerServicePort {
    Customer create(Customer customer);

    void deleteBy(Long id);

    Customer update(Long id, Customer customer);

    Customer findByCPF(String cpf);
}
