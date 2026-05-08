package distribuidora_api.ui_interface.controller;

import distribuidora_api.application.dto.movimentacao.MovimentacaoCreateDTO;
import distribuidora_api.application.dto.movimentacao.MovimentacaoResponseDTO;
import distribuidora_api.application.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
@Tag(name = "Movimentações", description = "Endpoints para registro de entradas e saídas de estoque")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping
    @Operation(summary = "Registrar nova movimentação", description = "Registra uma Entrada ou Saída e atualiza automaticamente o saldo do estoque do insumo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimentação registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou Estoque Insuficiente")
    })
    public ResponseEntity<MovimentacaoResponseDTO> registrarMovimentacao(@RequestBody @Valid MovimentacaoCreateDTO dto){
        MovimentacaoResponseDTO movimentacaoResponseDTO = movimentacaoService.registrarMovimentacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacaoResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar histórico", description = "Retorna o extrato completo de todas as movimentações realizadas.")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(movimentacaoService.listarTodas());
    }
}
