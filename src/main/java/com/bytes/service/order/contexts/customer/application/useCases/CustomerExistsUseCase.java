package com.bytes.service.order.contexts.customer.application.useCases;

import com.bytes.service.order.contexts.customer.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.contexts.shared.useCases.CustomerExistsUseCasePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerExistsUseCase implements CustomerExistsUseCasePort {
    @Autowired
    private CustomerRepositoryPort customerRepository;
    @Override
    public boolean execute(Long id) {
        return customerRepository.findById(id).isPresent();
    }
}
