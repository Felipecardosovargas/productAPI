package com.teach.productAPI.controller;

import com.teach.productAPI.dto.ProductRequestDTO;
import com.teach.productAPI.dto.ProductResponseDTO;
import com.teach.productAPI.exeptions.GlobalExceptionHandler;
import com.teach.productAPI.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Retorna a lista de todos os produtos",
    description = "Pode ser filtrado por um range de data de criação (formato ISO 8601: yyyy-MM-ddTHH:mm:ss).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @Parameter(description = "Data de inicio para o filtro (formato yyyy-MM-dd'T'HH:mm:ss)", example =  "2023-01-01T00:00:00")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ProductResponseDTO> products = productService.findAllProducts(startDate, endDate);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Retorna os detalhes de um produto específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorDetails.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(
                                                        @Parameter(description = "ID do produto a ser buscado")
                                                        @PathVariable Long id) {
        ProductResponseDTO product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Adiciona um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorDetails.class)))
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> add(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Detalhes do produto a ser adicionado", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDTO.class)))
            @Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO newProduct = productService.addProduct(dto);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza parcialmente um produto (nome ou preço)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorDetails.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @Parameter(description = "ID do produto a ser atualizado")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Novos detalhes (nome ou preço) do produto", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDTO.class)))
            @Valid @RequestBody ProductResponseDTO dto) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Remove um produto da lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorDetails.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do produto a ser removido")
            @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
