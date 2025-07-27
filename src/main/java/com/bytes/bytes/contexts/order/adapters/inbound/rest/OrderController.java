package com.bytes.bytes.contexts.order.adapters.inbound.rest;

import com.bytes.bytes.contexts.order.adapters.inbound.dtos.OrderResponseDTO;
import com.bytes.bytes.contexts.order.adapters.inbound.dtos.UpdateOrderReq;
import com.bytes.bytes.contexts.order.application.OrderService;
import com.bytes.bytes.contexts.order.domain.models.Order;
import com.bytes.bytes.contexts.order.domain.models.OrderStatus;
import com.bytes.bytes.contexts.order.domain.models.dtos.CreateOrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Order", description = "Operações realizadas pelo estabelicimento")
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Cria pedido")
    @PostMapping()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class)
            )),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    public ResponseEntity<Object> createOrder(@RequestBody CreateOrderDTO order) {
        Order newOrder = orderService.createOrder(order);
        return ResponseEntity.ok().body(newOrder);
    }

    @Operation(summary = "Atualiza pedido")
    @PutMapping("/status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class)
            )),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> updateStatus(@RequestBody UpdateOrderReq order, HttpServletRequest request) {
        var usedId = Long.parseLong(request.getAttribute("user_id").toString());
        orderService.updateStatus(order.id(), order.status(), usedId);
        return ResponseEntity.ok().body(order);
    }

    @Operation(summary = "Cancela pedido")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class)
            )),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> cancel(@PathVariable Long id, HttpServletRequest request) {
        var usedId = Long.parseLong(request.getAttribute("user_id").toString());
        orderService.cancelOrder(id, usedId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Busca pedido por status")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Order.class))

            )),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<Object> findOrderByStatus(@PathVariable String status) {
        return ResponseEntity.ok().body(orderService.findOrderByStatus(OrderStatus.fromString(status))
                .stream()
                .map(o -> new OrderResponseDTO(o, o.timeElapsedSinceLastStatus().toMillis(),o.totalTimeElapsed().toMillis()))
                .toList());
    }

    @Operation(summary = "Busca pedido por id")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class)
            )),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok().body(new OrderResponseDTO(order, order.timeElapsedSinceLastStatus().toMillis(), order.totalTimeElapsed().toMillis()));
    }
}
