package com.bytes.service.order.application.useCases;

import com.bytes.service.order.domain.models.Customer;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

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