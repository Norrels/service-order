package com.bytes.service.order.adapters.outbound.persistence.repositories;

import com.bytes.service.order.domain.models.Customer;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.mapper.CustomerMapper;

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
