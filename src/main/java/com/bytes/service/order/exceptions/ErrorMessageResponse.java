package com.bytes.service.order.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Representa a estrutura de um erro no sistema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {

    @Schema(description = "Mensagem de erro associada ao campo", example = "Valor inválido")
    String message;
};
