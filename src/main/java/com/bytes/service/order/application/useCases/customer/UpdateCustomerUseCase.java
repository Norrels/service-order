package com.bytes.service.order.application.useCases.customer;

import com.bytes.service.order.domain.models.Customer;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

public class UpdateCustomerUseCase {
    private final CustomerRepositoryPort customerRepository;

    public UpdateCustomerUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(Long id, Customer customerToUpdate) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        if (!customer.getEmail().equals(customerToUpdate.getEmail()) && customerRepository.existsByEmail(customerToUpdate.getEmail())) {
            throw new BusinessException("Este email já está em uso");
        }

        if (!customer.getCpf().equals(customerToUpdate.getCpf()) && customerRepository.existsByCpf(customerToUpdate.getCpf())) {
            throw new BusinessException("Este CPF já está cadastrado");
        }

        customer.update(customerToUpdate.getCpf(), customerToUpdate.getEmail(), customerToUpdate.getName(), customerToUpdate.getPhone());
        return customerRepository.save(customer);
    }
}
