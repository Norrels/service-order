package com.bytes.service.order.contexts.payment.adapters.inbound.rest;

import com.bytes.service.order.contexts.payment.adapters.inbound.dtos.PaymentDTO;
import com.bytes.service.order.contexts.payment.application.PaymentService;
import com.bytes.service.order.contexts.payment.domain.models.Payment;
import com.bytes.service.order.exceptions.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/payment")
@Tag(name = "Payment", description = "Endpoints de pagamento")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}/pay")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Payment.class)
            )),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    @Operation(summary = "Realizar pagamento")
    public ResponseEntity<Object> payOrder(@PathVariable Long orderId, @RequestBody PaymentDTO paymentOrder) {
        if(!Objects.equals(paymentOrder.getOrder_id(), orderId)){
            throw new BusinessException("O id do pedido é diferente do id passado como paramêtro");
        }
        Payment payment = paymentService.create(new Payment(null, orderId, paymentOrder.getPaymentType(), paymentOrder.getTotal(), paymentOrder.getExternal_id()));
        return ResponseEntity.ok().body(payment);
    }

    @GetMapping("/{orderId}/status")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Verificar status do pagamento, no integrador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "object", example = "{\"status\": \"PAIED\"}")
            )),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    public ResponseEntity<Object> statusOrder(@PathVariable Long orderId) {
        paymentService.findByOrderId(orderId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "PAIED");
        return ResponseEntity.ok(response);
    }
}
