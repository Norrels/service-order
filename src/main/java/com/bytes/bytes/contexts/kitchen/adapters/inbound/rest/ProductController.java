package com.bytes.bytes.contexts.kitchen.adapters.inbound.rest;

import com.bytes.bytes.contexts.kitchen.adapters.inbound.dtos.ProductRequest;
import com.bytes.bytes.contexts.kitchen.application.ProductService;
import com.bytes.bytes.contexts.kitchen.domain.models.Product;
import com.bytes.bytes.contexts.kitchen.domain.models.ProductCategory;
import com.bytes.bytes.contexts.kitchen.domain.port.outbound.ImageServicePort;
import com.bytes.bytes.contexts.kitchen.utils.ProductMapper;
import com.bytes.bytes.contexts.shared.dtos.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Product", description = "Operações realizadas pelo estabelicimento em relação aos produtos")
@RequestMapping("/kitchen/product")
public class ProductController {
    private final ProductService productService;
    private final ImageServicePort imageService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ImageServicePort imageService, ProductMapper productMapper) {
        this.productService = productService;
        this.imageService = imageService;
        this.productMapper = productMapper;
    }

    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Cria produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @Schema(implementation = Product.class)
            )),
            @ApiResponse(responseCode = "400", ref = "Validation"),
            @ApiResponse(responseCode = "403", ref = "ForbiddenAdmin"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request) {
        var productId = Long.parseLong(request.getAttribute("user_id").toString());
        Product productToSave = productMapper.toProduct(productRequest, productId);
        Product product = productService.createProduct(productToSave);
        return ResponseEntity.ok().body(product);
    }

    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Atualiza produto")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @Schema(implementation = Product.class)
            )),
            @ApiResponse(responseCode = "400", ref = "Validation"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource"),
            @ApiResponse(responseCode = "403", ref = "ForbiddenAdmin"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest, HttpServletRequest request) {
        var productId = Long.parseLong(request.getAttribute("user_id").toString());
        Product productToUpdate = productMapper.toProduct(productRequest, productId);
        Product product = productService.updateProduct(id, productToUpdate);
        return ResponseEntity.ok().body(product);
    }

    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Deleta produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDTO.class)
            )),
            @ApiResponse(responseCode = "403", ref = "ForbiddenAdmin"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(new ApiResponseDTO("Produto deletado com sucesso"));
    }

    @Operation(summary = "Busca produto por categoria")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Product.class))
    ))
    @GetMapping("/category/{category}")
    public ResponseEntity<Object> findProductByCategory(@PathVariable String category) {
        return ResponseEntity.ok().body(productService.findProductByCategory(ProductCategory.fromString(category)));
    }

    @SecurityRequirement(name = "jwt_auth")
    @PostMapping(value = "{productId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )),

            @ApiResponse(responseCode = "403", ref = "ForbiddenAdmin"),
            @ApiResponse(responseCode = "404", ref = "NotFoundResource")
    })
    @Operation(summary = "Upload de imagem")
    public ResponseEntity<Object> uploadImage(@RequestPart("file") MultipartFile file, @PathVariable("productId") Long productId) {
        try {
            productService.findProductById(productId);
            String imageUrl = imageService.uploadImage(file.getBytes());
            Product product = productService.updateImageUrl(productId, imageUrl);
            return ResponseEntity.ok().body(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro a salvar a imagem");
        }
    }
}
