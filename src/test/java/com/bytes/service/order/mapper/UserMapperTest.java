package com.bytes.service.order.mapper;

import com.bytes.service.order.adapters.inbound.dtos.UserRequest;
import com.bytes.service.order.adapters.outbound.persistence.entities.UserEntity;
import com.bytes.service.order.domain.models.User;
import com.bytes.service.order.domain.models.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    @DisplayName("Should map User domain model to UserEntity")
    void shouldMapUserToUserEntity() {
        User user = new User(1L, "João Silva", "joao@example.com", UserRole.ADMIN, "password123", true);

        UserEntity entity = userMapper.toUserEntity(user);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("João Silva", entity.getName());
        assertEquals("joao@example.com", entity.getEmail());
        assertEquals(UserRole.ADMIN, entity.getRole());
        assertEquals("password123", entity.getPassword());
        assertTrue(entity.isActive());
    }

    @Test
    @DisplayName("Should map UserEntity to User domain model")
    void shouldMapUserEntityToUser() {
        UserEntity entity = new UserEntity();
        entity.setId(2L);
        entity.setName("Maria Silva");
        entity.setEmail("maria@example.com");
        entity.setRole(UserRole.EMPLOYEE);
        entity.setPassword("mypassword");
        entity.setActive(false);

        User user = userMapper.toUser(entity);

        assertNotNull(user);
        assertEquals(2L, user.getId());
        assertEquals("Maria Silva", user.getName());
        assertEquals("maria@example.com", user.getEmail());
        assertEquals(UserRole.EMPLOYEE, user.getRole());
        assertEquals("mypassword", user.getPassword());
        assertFalse(user.isActive());
    }

    @Test
    @DisplayName("Should map UserRequest to User domain model ignoring id and active")
    void shouldMapUserRequestToUser() {
        UserRequest request = new UserRequest();
        request.setName("Carlos Santos");
        request.setEmail("carlos@example.com");
        request.setPassword("newpassword");
        request.setCpf("12345678901");
        request.setRole(UserRole.ADMIN);

        User user = userMapper.toUser(request);

        assertNotNull(user);
        assertNull(user.getId()); // Should be ignored according to mapping
        assertEquals("Carlos Santos", user.getName());
        assertEquals("carlos@example.com", user.getEmail());
        assertEquals(UserRole.ADMIN, user.getRole());
        assertEquals("newpassword", user.getPassword());
        assertFalse(user.isActive()); // Should be ignored/default according to mapping
    }

    @Test
    @DisplayName("Should handle null User domain model")
    void shouldHandleNullUser() {
        UserEntity entity = userMapper.toUserEntity(null);
        assertNull(entity);
    }

    @Test
    @DisplayName("Should handle null UserEntity")
    void shouldHandleNullUserEntity() {
        User user = userMapper.toUser((UserEntity) null);
        assertNull(user);
    }

    @Test
    @DisplayName("Should handle null UserRequest")
    void shouldHandleNullUserRequest() {
        User user = userMapper.toUser((UserRequest) null);
        assertNull(user);
    }

    @Test
    @DisplayName("Should map User with all user roles")
    void shouldMapUserWithAllRoles() {
        for (UserRole role : UserRole.values()) {
            User user = new User(1L, "Test User", "test@example.com", role, "password123", true);

            UserEntity entity = userMapper.toUserEntity(user);

            assertNotNull(entity);
            assertEquals(role, entity.getRole());
        }
    }

    @Test
    @DisplayName("Should map UserEntity with inactive status")
    void shouldMapInactiveUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(3L);
        entity.setName("Inactive User");
        entity.setEmail("inactive@example.com");
        entity.setRole(UserRole.EMPLOYEE);
        entity.setPassword("inactivepass");
        entity.setActive(false);

        User user = userMapper.toUser(entity);

        assertNotNull(user);
        assertEquals(3L, user.getId());
        assertEquals("Inactive User", user.getName());
        assertEquals("inactive@example.com", user.getEmail());
        assertEquals(UserRole.EMPLOYEE, user.getRole());
        assertEquals("inactivepass", user.getPassword());
        assertFalse(user.isActive());
    }

    @Test
    @DisplayName("Should preserve all fields when mapping User to UserEntity")
    void shouldPreserveAllFieldsUserToEntity() {
        User user = new User(5L, "Complete User", "complete@example.com", UserRole.ADMIN, "completepass", true);

        UserEntity entity = userMapper.toUserEntity(user);

        assertNotNull(entity);
        assertEquals(5L, entity.getId());
        assertEquals("Complete User", entity.getName());
        assertEquals("complete@example.com", entity.getEmail());
        assertEquals(UserRole.ADMIN, entity.getRole());
        assertEquals("completepass", entity.getPassword());
        assertTrue(entity.isActive());
    }
}