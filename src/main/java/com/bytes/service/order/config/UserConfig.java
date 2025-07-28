package com.bytes.service.order.config;

import com.bytes.service.order.application.UserService;
import com.bytes.service.order.application.useCases.user.AuthenticateUserUseCase;
import com.bytes.service.order.application.useCases.user.CreateUserUseCase;
import com.bytes.service.order.application.useCases.user.DeleteUserUseCase;
import com.bytes.service.order.application.useCases.user.UpdateUserUseCase;
import com.bytes.service.order.domain.outbound.TokenProviderPort;
import com.bytes.service.order.domain.outbound.UserRepositoryPort;
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