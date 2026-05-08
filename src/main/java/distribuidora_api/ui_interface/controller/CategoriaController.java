package distribuidora_api.ui_interface.controller;

import distribuidora_api.application.dto.categoria.CategoriaCreateDTO;
import distribuidora_api.application.dto.categoria.CategoriaResponseDTO;
import distribuidora_api.application.service.CategoriaService;
import distribuidora_api.domain.entity.Categoria;
import distribuidora_api.domain.repository.CategoriaRepository;
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
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Endpoints para o gerenciamento das categorias de insumo")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @Operation(summary = "Cadastrar uma nova categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<CategoriaResponseDTO> cadastrar(@RequestBody @Valid CategoriaCreateDTO dto) {
        CategoriaResponseDTO responseDTO = categoriaService.criarCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todas as categorias")
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }
}
