package com.bytes.service.order.contexts.kitchen.domain.port.outbound;

import com.bytes.service.order.contexts.kitchen.domain.models.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
