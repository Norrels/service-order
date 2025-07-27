package com.bytes.bytes.contexts.customer.application.useCases;

import com.bytes.bytes.contexts.customer.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.bytes.exceptions.ResourceNotFoundException;

public class DeleteCustomerUseCase {
    private final CustomerRepositoryPort customerRepository;

    public DeleteCustomerUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void execute(Long id) {
        customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        customerRepository.delete(id);
    }
}