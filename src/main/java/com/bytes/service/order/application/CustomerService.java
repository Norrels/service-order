package com.bytes.service.order.application;

import com.bytes.service.order.application.useCases.customer.CreateCustomerUseCase;
import com.bytes.service.order.application.useCases.customer.DeleteCustomerUseCase;
import com.bytes.service.order.application.useCases.customer.FindCustomerByCPFUseCase;
import com.bytes.service.order.application.useCases.customer.UpdateCustomerUseCase;
import com.bytes.service.order.domain.models.Customer;
import com.bytes.service.order.domain.ports.inbound.CustomerServicePort;

public class CustomerService implements CustomerServicePort {
    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final FindCustomerByCPFUseCase findCustomerByCPFUseCase;

    public CustomerService(CreateCustomerUseCase createCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, UpdateCustomerUseCase updateCustomerUseCase, FindCustomerByCPFUseCase findCustomerByCPFUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.findCustomerByCPFUseCase = findCustomerByCPFUseCase;
    }

    @Override
    public Customer create(Customer customer) {
        return createCustomerUseCase.execute(customer);
    }

    @Override
    public void deleteBy(Long id) {
        deleteCustomerUseCase.execute(id);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        return updateCustomerUseCase.execute(id, customer);
    }

    @Override
    public Customer findByCPF(String cpf) {
        return findCustomerByCPFUseCase.execute(cpf);
    }
}