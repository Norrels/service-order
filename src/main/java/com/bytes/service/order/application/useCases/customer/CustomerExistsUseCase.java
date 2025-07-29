package com.bytes.service.order.application.useCases.customer;

import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerExistsUseCase {
    @Autowired
    private CustomerRepositoryPort customerRepository;

    public boolean execute(Long id) {
        return customerRepository.findById(id).isPresent();
    }
}
