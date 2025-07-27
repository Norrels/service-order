package com.bytes.bytes.contexts.customer.adapters.inbound.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerReq {
    @Size(min = 11, max = 11, message = "O CPF teve ter 11 números")
    private String cpf;

    @Email(message = "Email inválido")
    private String email;

    @NotNull
    private String name;

    @Size(min = 9, max = 9, message = "O número de telefone deve ter 9 dígitos")
    private String phone;
}
