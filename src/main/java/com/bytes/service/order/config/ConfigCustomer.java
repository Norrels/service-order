package com.bytes.service.order.config;

import com.bytes.service.order.application.CustomerService;
import com.bytes.service.order.application.useCases.CreateCustomerUseCase;
import com.bytes.service.order.application.useCases.DeleteCustomerUseCase;
import com.bytes.service.order.application.useCases.FindCustomerByCPFUseCase;
import com.bytes.service.order.application.useCases.UpdateCustomerUseCase;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigCustomer {

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new CreateCustomerUseCase(customerRepositoryPort);
    }

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new DeleteCustomerUseCase(customerRepositoryPort);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new UpdateCustomerUseCase(customerRepositoryPort);
    }

    @Bean
    public FindCustomerByCPFUseCase findCustomerByCPFUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new FindCustomerByCPFUseCase(customerRepositoryPort);
    }

    @Bean
    public CustomerService customerService(CreateCustomerUseCase createCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, UpdateCustomerUseCase updateCustomerUseCase, FindCustomerByCPFUseCase findCustomerByCPFUseCase) {
        return new CustomerService(createCustomerUseCase, deleteCustomerUseCase, updateCustomerUseCase, findCustomerByCPFUseCase);
    }
}
