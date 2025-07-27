package com.bytes.service.order.contexts.customer.adapters.outbound.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 11, max = 11, message = "O CPF teve ter 11 números")
    private String cpf;

    @Email(message = "Email inválido")
    private String email;

    @NotNull
    private String name;

    @Size(min = 9, max = 9, message = "O número de telefone deve ter 9 dígitos")
    private String phone;
}
