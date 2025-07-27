package com.bytes.service.order.contexts.kitchen.adapters.outbound.persistence.repositories;

import com.bytes.service.order.contexts.kitchen.domain.port.outbound.UserRepositoryPort;
import com.bytes.service.order.contexts.kitchen.domain.models.User;
import com.bytes.service.order.contexts.kitchen.utils.UserMapper;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserRepository repository;

    private final UserMapper userMapper;

    public UserRepositoryAdapter(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        return userMapper.toUser(repository.save(userMapper.toUserEntity(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(userMapper::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(userMapper::toUser);
    }

}
