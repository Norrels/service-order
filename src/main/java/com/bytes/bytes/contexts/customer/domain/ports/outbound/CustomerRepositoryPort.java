package com.bytes.bytes.contexts.customer.domain.ports.outbound;

import com.bytes.bytes.contexts.customer.domain.models.Customer;

import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer c);

    void delete(Long id);

    Optional<Customer> findByCPF(String cpf);

    Optional<Customer> findById(Long id);

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
