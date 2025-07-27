package com.bytes.service.order.contexts.kitchen.application.useCases.user;

import com.bytes.service.order.contexts.kitchen.domain.models.User;
import com.bytes.service.order.contexts.kitchen.domain.port.outbound.TokenProviderPort;
import com.bytes.service.order.contexts.kitchen.domain.port.outbound.UserRepositoryPort;
import com.bytes.service.order.exceptions.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class AuthenticateUserUseCase {
    private final UserRepositoryPort userRepository;
    private final TokenProviderPort tokenProviderPort;
    private final PasswordEncoder passwordEncoder;

    public AuthenticateUserUseCase(UserRepositoryPort userRepository, TokenProviderPort tokenProviderPort, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProviderPort = tokenProviderPort;
        this.passwordEncoder = passwordEncoder;
    }

    public String execute(String email, String password) {
        User user = userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new BusinessException("Email ou senha incorretos"));

        if (!user.isActive()) {
            throw new BusinessException("Usuário inativo");
        }

        return tokenProviderPort.generate(user.getId().toString(), List.of(user.getRole().toString()));
    }
}
