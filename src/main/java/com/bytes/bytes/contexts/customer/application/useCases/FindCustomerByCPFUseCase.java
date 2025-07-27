package com.bytes.bytes.contexts.customer.application.useCases;

import com.bytes.bytes.contexts.customer.domain.models.Customer;
import com.bytes.bytes.contexts.customer.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.bytes.exceptions.BusinessException;
import com.bytes.bytes.exceptions.ResourceNotFoundException;

public class FindCustomerByCPFUseCase {
    private final CustomerRepositoryPort customerRepository;

    public FindCustomerByCPFUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(String cpf) {
        if(cpf.length() != 11) {
            throw new BusinessException("CPF inválido");
        }

        return customerRepository.findByCPF(cpf).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }
}