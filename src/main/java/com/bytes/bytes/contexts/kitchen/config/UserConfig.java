package com.bytes.bytes.contexts.kitchen.config;

import com.bytes.bytes.contexts.kitchen.application.UserService;
import com.bytes.bytes.contexts.kitchen.application.useCases.user.*;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.TokenProviderPort;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        return new CreateUserUseCase(userRepositoryPort, passwordEncoder);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        return new UpdateUserUseCase(userRepositoryPort, passwordEncoder);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepositoryPort userRepositoryPort) {
        return new DeleteUserUseCase(userRepositoryPort);
    }

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(UserRepositoryPort userRepositoryPort, TokenProviderPort tokenProviderPort, PasswordEncoder passwordEncoder) {
        return new AuthenticateUserUseCase(userRepositoryPort, tokenProviderPort, passwordEncoder);
    }

    @Bean
    public UserService userService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, AuthenticateUserUseCase authenticateUserUseCase) {
        return new UserService(createUserUseCase, updateUserUseCase, deleteUserUseCase, authenticateUserUseCase);
    }
}