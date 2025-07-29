package com.bytes.service.order.adapters.inbound.dtos;

import com.bytes.service.order.domain.models.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, max = 255, message = "Senha deve ter no mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 12, max = 12, message = "O CPF deve ter no mínimo 11 caracteres")
    private String cpf;

    @NotNull(message = "Função do usuário é obrigatória")
    private UserRole role;
}
