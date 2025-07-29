package com.bytes.service.order.application.useCases.user;

import com.bytes.service.order.domain.models.User;
import com.bytes.service.order.domain.outbound.UserRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;
import com.bytes.service.order.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UpdateUserUseCase {
    private final UserRepositoryPort userRepository;

    private final PasswordEncoder passwordEncoder;

    public UpdateUserUseCase(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(Long id, User userToUpdate) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!user.getEmail().equals(userToUpdate.getEmail())) {
            userRepository.findByEmail(userToUpdate.getEmail()).ifPresent((u) -> {
                throw new BusinessException("O email já foi cadastrado");
            });
        }

        user.update(userToUpdate);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
