package com.bytes.service.order.contexts.kitchen.application.useCases.user;

import com.bytes.service.order.contexts.kitchen.domain.models.User;
import com.bytes.service.order.contexts.kitchen.domain.port.outbound.UserRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateUserUseCase {
    private final UserRepositoryPort userRepository;

    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent((u) -> {
            throw new BusinessException("Usuário já existente");
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }
}
