package distribuidora_api.ui_interface.controller;

import distribuidora_api.application.dto.insumo.InsumoCreateDTO;
import distribuidora_api.application.dto.insumo.InsumoResponseDTO;
import distribuidora_api.application.service.InsumoService;
import distribuidora_api.domain.entity.Insumo;
import distribuidora_api.domain.repository.InsumoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
@Tag(name = "Insumos", description = "Endpoints para gestão de materiais e controle de estoque")
public class InsumoController {

    private final InsumoService insumoService;

    @PostMapping
    @Operation(summary = "Cadastrar um novo insumo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Insumo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou Categoria inexistente")
    })
    public ResponseEntity<InsumoResponseDTO> cadastrarInsumo(@RequestBody @Valid InsumoCreateDTO dto) {
        InsumoResponseDTO responseDTO = insumoService.cadastrarinsumo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os insumos")
    public ResponseEntity<List<InsumoResponseDTO>> listarInsumos() {
        return ResponseEntity.ok(insumoService.listarTodos());
    }

    @GetMapping("/alertas")
    @Operation(summary = "Listar insumos com estoque crítico", description = "Retorna todos os insumos cujo estoque atual está igual ou abaixo do estoque mínimo.")
    public  ResponseEntity<List<InsumoResponseDTO>> listarInsumosEmAlerta() {
        return ResponseEntity.ok(insumoService.listarItensEmAlerta());
    }
}
