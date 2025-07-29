package com.bytes.service.order.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User validUser;
    
    @BeforeEach
    void setUp() {
        validUser = new User(1L, "João Silva", "joao@email.com", UserRole.ADMIN, "password123", true);
    }
    
    @Test
    @DisplayName("Should create user with valid data")
    void shouldCreateUserWithValidData() {
        assertNotNull(validUser);
        assertEquals(1L, validUser.getId());
        assertEquals("João Silva", validUser.getName());
        assertEquals("joao@email.com", validUser.getEmail());
        assertEquals(UserRole.ADMIN, validUser.getRole());
        assertEquals("password123", validUser.getPassword());
        assertTrue(validUser.isActive());
    }
    
    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        User user = new User(1L, null, "joao@email.com", UserRole.ADMIN, "password123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        User user = new User(1L, "", "joao@email.com", UserRole.ADMIN, "password123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        User user = new User(1L, "João Silva", null, UserRole.ADMIN, "password123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when email is empty")
    void shouldThrowExceptionWhenEmailIsEmpty() {
        User user = new User(1L, "João Silva", "", UserRole.ADMIN, "password123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when password is null")
    void shouldThrowExceptionWhenPasswordIsNull() {
        User user = new User(1L, "João Silva", "joao@email.com", UserRole.ADMIN, null, true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when password is empty")
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        User user = new User(1L, "João Silva", "joao@email.com", UserRole.ADMIN, "", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when password is too short")
    void shouldThrowExceptionWhenPasswordIsTooShort() {
        User user = new User(1L, "João Silva", "joao@email.com", UserRole.ADMIN, "1234567", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when password is too long")
    void shouldThrowExceptionWhenPasswordIsTooLong() {
        User user = new User(1L, "João Silva", "joao@email.com", UserRole.ADMIN, "1234567890123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when email is invalid - no @")
    void shouldThrowExceptionWhenEmailHasNoAt() {
        User user = new User(1L, "João Silva", "joaoemail.com", UserRole.ADMIN, "password123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when email is invalid - no dot")
    void shouldThrowExceptionWhenEmailHasNoDot() {
        User user = new User(1L, "João Silva", "joao@email", UserRole.ADMIN, "password123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when email is too short")
    void shouldThrowExceptionWhenEmailIsTooShort() {
        User user = new User(1L, "João Silva", "a@b.", UserRole.ADMIN, "password123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should throw exception when email contains spaces")
    void shouldThrowExceptionWhenEmailContainsSpaces() {
        User user = new User(1L, "João Silva", "joao @email.com", UserRole.ADMIN, "password123", true);
        
        assertThrows(IllegalArgumentException.class, user::validate);
    }
    
    @Test
    @DisplayName("Should accept password with exactly 8 characters")
    void shouldAcceptPasswordWith8Characters() {
        User user = new User(1L, "João Silva", "joao@email.com", UserRole.ADMIN, "12345678", true);
        
        assertDoesNotThrow(user::validate);
    }
    
    @Test
    @DisplayName("Should accept password with exactly 12 characters")
    void shouldAcceptPasswordWith12Characters() {
        User user = new User(1L, "João Silva", "joao@email.com", UserRole.ADMIN, "123456789012", true);
        
        assertDoesNotThrow(user::validate);
    }
    
    @Test
    @DisplayName("Should update user with valid data")
    void shouldUpdateUserWithValidData() {
        User updatedUser = new User(2L, "Maria Santos", "maria@email.com", UserRole.ADMIN, "newpass123", false);
        
        validUser.update(updatedUser);
        
        assertEquals("Maria Santos", validUser.getName());
        assertEquals("maria@email.com", validUser.getEmail());
        assertEquals("newpass123", validUser.getPassword());
        assertEquals(UserRole.ADMIN, validUser.getRole());
        assertTrue(validUser.isActive());
    }
    
    @Test
    @DisplayName("Should throw exception when updating with invalid user")
    void shouldThrowExceptionWhenUpdatingWithInvalidUser() {
        User invalidUser = new User(2L, null, "maria@email.com", UserRole.ADMIN, "newpass123", false);
        
        assertThrows(IllegalArgumentException.class, () -> {
            validUser.update(invalidUser);
        });
    }
}