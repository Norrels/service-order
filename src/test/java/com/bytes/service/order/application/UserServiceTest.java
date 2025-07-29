package com.bytes.service.order.application;

import com.bytes.service.order.application.useCases.user.AuthenticateUserUseCase;
import com.bytes.service.order.application.useCases.user.CreateUserUseCase;
import com.bytes.service.order.application.useCases.user.DeleteUserUseCase;
import com.bytes.service.order.application.useCases.user.UpdateUserUseCase;
import com.bytes.service.order.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private CreateUserUseCase createUserUseCase;
    
    @Mock
    private UpdateUserUseCase updateUserUseCase;
    
    @Mock
    private DeleteUserUseCase deleteUserUseCase;
    
    @Mock
    private AuthenticateUserUseCase authenticateUserUseCase;
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService(
            createUserUseCase,
            updateUserUseCase,
            deleteUserUseCase,
            authenticateUserUseCase
        );
    }
    
    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        User user = mock(User.class);
        User expectedUser = mock(User.class);
        
        when(createUserUseCase.execute(user)).thenReturn(expectedUser);
        
        User result = userService.createUser(user);
        
        assertEquals(expectedUser, result);
        verify(createUserUseCase).execute(user);
    }
    
    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
        Long userId = 1L;
        User user = mock(User.class);
        User expectedUser = mock(User.class);
        
        when(updateUserUseCase.execute(userId, user)).thenReturn(expectedUser);
        
        User result = userService.update(userId, user);
        
        assertEquals(expectedUser, result);
        verify(updateUserUseCase).execute(userId, user);
    }
    
    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;
        User expectedUser = mock(User.class);
        
        when(deleteUserUseCase.execute(userId)).thenReturn(expectedUser);
        
        User result = userService.delete(userId);
        
        assertEquals(expectedUser, result);
        verify(deleteUserUseCase).execute(userId);
    }
    
    @Test
    @DisplayName("Should authenticate user successfully")
    void shouldAuthenticateUserSuccessfully() {
        String email = "user@email.com";
        String password = "password123";
        String expectedToken = "jwt-token-123";
        
        when(authenticateUserUseCase.execute(email, password)).thenReturn(expectedToken);
        
        String result = userService.autenticate(email, password);
        
        assertEquals(expectedToken, result);
        verify(authenticateUserUseCase).execute(email, password);
    }
    
    @Test
    @DisplayName("Should handle null user for create")
    void shouldHandleNullUserForCreate() {
        when(createUserUseCase.execute(null)).thenThrow(new IllegalArgumentException("User cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(null);
        });
        
        verify(createUserUseCase).execute(null);
    }
    
    @Test
    @DisplayName("Should handle null user id for update")
    void shouldHandleNullUserIdForUpdate() {
        User user = mock(User.class);
        when(updateUserUseCase.execute(null, user)).thenThrow(new IllegalArgumentException("User ID cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.update(null, user);
        });
        
        verify(updateUserUseCase).execute(null, user);
    }
    
    @Test
    @DisplayName("Should handle null user for update")
    void shouldHandleNullUserForUpdate() {
        Long userId = 1L;
        when(updateUserUseCase.execute(userId, null)).thenThrow(new IllegalArgumentException("User cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.update(userId, null);
        });
        
        verify(updateUserUseCase).execute(userId, null);
    }
    
    @Test
    @DisplayName("Should handle null user id for delete")
    void shouldHandleNullUserIdForDelete() {
        when(deleteUserUseCase.execute(null)).thenThrow(new IllegalArgumentException("User ID cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.delete(null);
        });
        
        verify(deleteUserUseCase).execute(null);
    }
    
    @Test
    @DisplayName("Should handle null email for authenticate")
    void shouldHandleNullEmailForAuthenticate() {
        String password = "password123";
        when(authenticateUserUseCase.execute(null, password)).thenThrow(new IllegalArgumentException("Email cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.autenticate(null, password);
        });
        
        verify(authenticateUserUseCase).execute(null, password);
    }
    
    @Test
    @DisplayName("Should handle null password for authenticate")
    void shouldHandleNullPasswordForAuthenticate() {
        String email = "user@email.com";
        when(authenticateUserUseCase.execute(email, null)).thenThrow(new IllegalArgumentException("Password cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.autenticate(email, null);
        });
        
        verify(authenticateUserUseCase).execute(email, null);
    }
    
    @Test
    @DisplayName("Should handle empty email for authenticate")
    void shouldHandleEmptyEmailForAuthenticate() {
        String email = "";
        String password = "password123";
        when(authenticateUserUseCase.execute(email, password)).thenThrow(new IllegalArgumentException("Email cannot be empty"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.autenticate(email, password);
        });
        
        verify(authenticateUserUseCase).execute(email, password);
    }
    
    @Test
    @DisplayName("Should handle empty password for authenticate")
    void shouldHandleEmptyPasswordForAuthenticate() {
        String email = "user@email.com";
        String password = "";
        when(authenticateUserUseCase.execute(email, password)).thenThrow(new IllegalArgumentException("Password cannot be empty"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.autenticate(email, password);
        });
        
        verify(authenticateUserUseCase).execute(email, password);
    }
}