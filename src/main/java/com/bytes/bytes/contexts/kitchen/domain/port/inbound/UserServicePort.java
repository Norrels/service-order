package com.bytes.bytes.contexts.kitchen.domain.port.inbound;

import com.bytes.bytes.contexts.kitchen.domain.models.User;

public interface UserServicePort {
    User createUser(User user);
    User update(Long id, User user);
    User delete(Long id);
    String autenticate(String email, String password);
}
