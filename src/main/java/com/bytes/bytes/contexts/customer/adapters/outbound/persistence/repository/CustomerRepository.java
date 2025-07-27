package com.bytes.bytes.contexts.customer.adapters.outbound.persistence.repository;

import com.bytes.bytes.contexts.customer.adapters.outbound.persistence.entity.CustomerEntity;
import com.bytes.bytes.contexts.customer.domain.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
