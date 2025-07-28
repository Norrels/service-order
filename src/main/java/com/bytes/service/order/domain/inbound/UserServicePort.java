package com.bytes.service.order.domain.inbound;

import com.bytes.service.order.domain.models.User;

public interface UserServicePort {
    User createUser(User user);
    User update(Long id, User user);
    User delete(Long id);
    String autenticate(String email, String password);
}
