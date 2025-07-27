package com.bytes.bytes.contexts.kitchen.application;

import com.bytes.bytes.contexts.kitchen.application.useCases.user.*;
import com.bytes.bytes.contexts.kitchen.domain.models.User;
import com.bytes.bytes.contexts.kitchen.domain.port.inbound.UserServicePort;

public class UserService implements UserServicePort {
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    public UserService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, AuthenticateUserUseCase authenticateUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @Override
    public User createUser(User user) {
        return createUserUseCase.execute(user);
    }

    @Override
    public User update(Long id, User user) {
      return updateUserUseCase.execute(id, user);
    }

    @Override
    public User delete(Long id) {
        return deleteUserUseCase.execute(id);
    }

    @Override
    public String autenticate(String email, String password) {
       return authenticateUserUseCase.execute(email, password);
    }
}
