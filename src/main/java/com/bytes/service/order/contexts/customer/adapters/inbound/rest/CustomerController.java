package com.bytes.service.order.contexts.customer.adapters.inbound.rest;

import com.bytes.service.order.contexts.customer.adapters.inbound.dtos.CustomerReq;
import com.bytes.service.order.contexts.customer.adapters.outbound.persistence.entity.CustomerEntity;
import com.bytes.service.order.contexts.customer.application.CustomerService;
import com.bytes.service.order.contexts.customer.domain.models.Customer;
import com.bytes.service.order.contexts.customer.mapper.CustomerMapper;
import com.bytes.service.order.exceptions.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer", description = "Endpoints de cliente")
public class CustomerController {
    private final CustomerService customerService;

    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @Operation(summary = "Cria cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Customer.class)
            )),
            @ApiResponse(responseCode = "400", ref = "Validation"),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
    })
    @PostMapping()
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerReq customerReq) {
        Customer customer = customerService.create(customerMapper.toCustomer(customerReq));
        return ResponseEntity.ok().body(customer);
    }

    @Operation(summary = "Busca cliente por CPF")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Customer.class)
            )),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<Object> findCustomerByCPF(@PathVariable String cpf) {
        Customer customer = customerService.findByCPF(cpf);
        return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Atualiza cliente")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Customer.class)
            )),
            @ApiResponse(responseCode = "400", ref = "Validation"),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource"),
            @ApiResponse(responseCode = "403", ref = "ForbiddenAdmin"),
    })
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerEntity customerReq) {
            if (!Objects.equals(customerReq.getId(), id)) {
                throw new BusinessException("O id enviado no corpo e na url são diferentes");
            }

            Customer customer = customerService.update(id, customerMapper.toCustomer(customerReq));
            return ResponseEntity.ok(customer);
    }
}
