package com.bytes.service.order.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "ErrorValidationField", description = "Representa a estrutura de um erro de validação de campo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorValidationField {
    @Schema(description = "Campo", example = "Nome")
    String field;

    @Schema(description = "Messagem de validação", example = "Nome não pode ser nulo")
    String message;
};
