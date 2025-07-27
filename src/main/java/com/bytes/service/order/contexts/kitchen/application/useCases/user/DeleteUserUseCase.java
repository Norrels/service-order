package com.bytes.service.order.contexts.kitchen.application.useCases.user;

import com.bytes.service.order.contexts.kitchen.domain.models.User;
import com.bytes.service.order.contexts.kitchen.domain.port.outbound.UserRepositoryPort;
import com.bytes.service.order.exceptions.ResourceNotFoundException;

public class DeleteUserUseCase {
    private final UserRepositoryPort userRepository;

    public DeleteUserUseCase(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        user.setActive(false);
        return userRepository.save(user);
    }
}
