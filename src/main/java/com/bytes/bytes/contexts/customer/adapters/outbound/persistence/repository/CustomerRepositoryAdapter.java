package com.bytes.bytes.contexts.customer.adapters.outbound.persistence.repository;

import com.bytes.bytes.contexts.customer.domain.models.Customer;
import com.bytes.bytes.contexts.customer.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.bytes.contexts.customer.mapper.CustomerMapper;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    public CustomerRepositoryAdapter(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Customer save(Customer c) {
        return mapper.toCustomer(repository.save(mapper.toCustomerEntity(c)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Customer> findByCPF(String cpf) {
        return repository.findByCpf(cpf).map(mapper::toCustomer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id).map(mapper::toCustomer);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }
}
