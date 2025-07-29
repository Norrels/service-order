package com.bytes.service.order.adapters.outbound.persistence.repositories;

import com.bytes.service.order.adapters.outbound.persistence.entities.UserEntity;
import com.bytes.service.order.domain.models.User;
import com.bytes.service.order.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private User mockUser;
    
    @Mock
    private UserEntity mockUserEntity;
    
    private UserRepositoryAdapter userRepositoryAdapter;
    
    @BeforeEach
    void setUp() {
        userRepositoryAdapter = new UserRepositoryAdapter(userRepository, userMapper);
    }
    
    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUserSuccessfully() {
        when(userMapper.toUserEntity(mockUser)).thenReturn(mockUserEntity);
        when(userRepository.save(mockUserEntity)).thenReturn(mockUserEntity);
        when(userMapper.toUser((UserEntity) mockUserEntity)).thenReturn(mockUser);
        
        User result = userRepositoryAdapter.save(mockUser);
        
        assertEquals(mockUser, result);
        verify(userMapper).toUserEntity(mockUser);
        verify(userRepository).save(mockUserEntity);
        verify(userMapper).toUser(mockUserEntity);
    }
    
    @Test
    @DisplayName("Should find user by ID successfully")
    void shouldFindUserByIDSuccessfully() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));
        when(userMapper.toUser((UserEntity) mockUserEntity)).thenReturn(mockUser);
        
        Optional<User> result = userRepositoryAdapter.findById(userId);
        
        assertTrue(result.isPresent());
        assertEquals(mockUser, result.get());
        verify(userRepository).findById(userId);
        verify(userMapper).toUser(mockUserEntity);
    }
    
    @Test
    @DisplayName("Should return empty when user not found by ID")
    void shouldReturnEmptyWhenUserNotFoundByID() {
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        Optional<User> result = userRepositoryAdapter.findById(userId);
        
        assertFalse(result.isPresent());
        verify(userRepository).findById(userId);
        verify(userMapper, never()).toUser(any(UserEntity.class));
    }
    
    @Test
    @DisplayName("Should find user by email successfully")
    void shouldFindUserByEmailSuccessfully() {
        String email = "user@email.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUserEntity));
        when(userMapper.toUser((UserEntity) mockUserEntity)).thenReturn(mockUser);
        
        Optional<User> result = userRepositoryAdapter.findByEmail(email);
        
        assertTrue(result.isPresent());
        assertEquals(mockUser, result.get());
        verify(userRepository).findByEmail(email);
        verify(userMapper).toUser(mockUserEntity);
    }
    
    @Test
    @DisplayName("Should return empty when user not found by email")
    void shouldReturnEmptyWhenUserNotFoundByEmail() {
        String email = "nonexistent@email.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        
        Optional<User> result = userRepositoryAdapter.findByEmail(email);
        
        assertFalse(result.isPresent());
        verify(userRepository).findByEmail(email);
        verify(userMapper, never()).toUser(any(UserEntity.class));
    }
    
    @Test
    @DisplayName("Should handle null user for save")
    void shouldHandleNullUserForSave() {
        when(userMapper.toUserEntity(null)).thenReturn(null);
        when(userRepository.save(null)).thenThrow(new IllegalArgumentException("User cannot be null"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userRepositoryAdapter.save(null);
        });
        
        verify(userMapper).toUserEntity(null);
        verify(userRepository).save(null);
    }
    
    @Test
    @DisplayName("Should handle repository exception for save")
    void shouldHandleRepositoryExceptionForSave() {
        when(userMapper.toUserEntity(mockUser)).thenReturn(mockUserEntity);
        when(userRepository.save(mockUserEntity)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            userRepositoryAdapter.save(mockUser);
        });
        
        verify(userMapper).toUserEntity(mockUser);
        verify(userRepository).save(mockUserEntity);
    }
    
    @Test
    @DisplayName("Should handle repository exception for findById")
    void shouldHandleRepositoryExceptionForFindById() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            userRepositoryAdapter.findById(userId);
        });
        
        verify(userRepository).findById(userId);
    }
    
    @Test
    @DisplayName("Should handle repository exception for findByEmail")
    void shouldHandleRepositoryExceptionForFindByEmail() {
        String email = "user@email.com";
        when(userRepository.findByEmail(email)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            userRepositoryAdapter.findByEmail(email);
        });
        
        verify(userRepository).findByEmail(email);
    }
    
    @Test
    @DisplayName("Should handle mapper exception for save")
    void shouldHandleMapperExceptionForSave() {
        when(userMapper.toUserEntity(mockUser)).thenThrow(new RuntimeException("Mapper error"));
        
        assertThrows(RuntimeException.class, () -> {
            userRepositoryAdapter.save(mockUser);
        });
        
        verify(userMapper).toUserEntity(mockUser);
        verify(userRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Should handle mapper exception for findById")
    void shouldHandleMapperExceptionForFindById() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUserEntity));
        when(userMapper.toUser(mockUserEntity)).thenThrow(new RuntimeException("Mapper error"));
        
        assertThrows(RuntimeException.class, () -> {
            userRepositoryAdapter.findById(userId);
        });
        
        verify(userRepository).findById(userId);
        verify(userMapper).toUser(mockUserEntity);
    }
    
    @Test
    @DisplayName("Should handle mapper exception for findByEmail")
    void shouldHandleMapperExceptionForFindByEmail() {
        String email = "user@email.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUserEntity));
        when(userMapper.toUser(mockUserEntity)).thenThrow(new RuntimeException("Mapper error"));
        
        assertThrows(RuntimeException.class, () -> {
            userRepositoryAdapter.findByEmail(email);
        });
        
        verify(userRepository).findByEmail(email);
        verify(userMapper).toUser(mockUserEntity);
    }
}