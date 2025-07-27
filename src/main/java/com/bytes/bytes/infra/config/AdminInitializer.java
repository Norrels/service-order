package com.bytes.bytes.infra.config;

import com.bytes.bytes.contexts.kitchen.domain.models.User;
import com.bytes.bytes.contexts.kitchen.domain.models.UserRole;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    @Autowired
    private UserRepositoryPort userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin.bytes@bytes.com").isEmpty()) {
            userRepository.save(new User(null, "Admin", "admin.bytes@bytes.com", UserRole.ADMIN,  passwordEncoder.encode("@dmin20251"), true));
            System.out.println("Usuário administrador criado com sucesso!");
        } else {
            System.out.println("Usuário administrador já existe.");
        }
    }
}
