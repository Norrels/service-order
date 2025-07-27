package com.bytes.service.order.contexts.kitchen.adapters.inbound.rest;

import com.bytes.service.order.contexts.kitchen.adapters.inbound.dtos.AuthUserDTO;
import com.bytes.service.order.contexts.kitchen.adapters.inbound.dtos.TokenDTO;
import com.bytes.service.order.contexts.kitchen.adapters.inbound.dtos.UserRequest;
import com.bytes.service.order.contexts.kitchen.application.UserService;
import com.bytes.service.order.contexts.kitchen.domain.models.User;
import com.bytes.service.order.contexts.kitchen.utils.UserMapper;
import com.bytes.service.order.contexts.shared.dtos.ApiResponseDTO;
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

@RestController
@Tag(name = "User", description = "Controle de usuários do estabelicimento")
@RequestMapping("/kitchen/user")
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService saveUserUseCase, UserMapper userMapper) {
        this.userService = saveUserUseCase;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Cria usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class)
            )),
            @ApiResponse(responseCode = "400", ref = "Validation"),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
            @ApiResponse(responseCode = "403", ref = "ForbiddenAdmin"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody UserRequest userRequest) {
        User user = userService.createUser(userMapper.toUser(userRequest));
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Atualiza usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class)
            )),
            @ApiResponse(responseCode = "400", ref = "Validation"),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
    })
    @SecurityRequirement(name = "jwt_auth")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        User user = userService.update(id, userMapper.toUser(userRequest));
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Desativa usuário")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource"),
            @ApiResponse(responseCode = "403", ref = "ForbiddenAdmin"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("disable/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().body(new ApiResponseDTO("Usuário desativado com sucesso"));
    }

    @Operation(summary = "Autentica usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TokenDTO.class)
            )),
            @ApiResponse(responseCode = "422", ref = "BusinessError"),
    })
    @PostMapping("authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthUserDTO authUserDTO) {
        String token = userService.autenticate(authUserDTO.email(), authUserDTO.password());
        return ResponseEntity.ok().body(new TokenDTO(token));
    }

}
