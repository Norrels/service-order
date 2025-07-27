package com.bytes.service.order.contexts.customer.application.useCases;

import com.bytes.service.order.contexts.customer.domain.models.Customer;
import com.bytes.service.order.contexts.customer.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;

public class CreateCustomerUseCase {
    private final CustomerRepositoryPort customerRepository;

    public CreateCustomerUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(Customer customer) {
        if(customerRepository.existsByCpf(customer.getCpf())) {
            throw new BusinessException("Este CPF já está cadastrado");
        }

        if(customerRepository.existsByEmail(customer.getEmail())) {
            throw new BusinessException("Este email já está cadastrado");
        }

        return customerRepository.save(customer);
    }
}
