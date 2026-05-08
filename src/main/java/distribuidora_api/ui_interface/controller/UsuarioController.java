package distribuidora_api.ui_interface.controller;

import distribuidora_api.application.dto.usuario.UsuarioCreateDTO;
import distribuidora_api.application.dto.usuario.UsuarioResponseDTO;
import distribuidora_api.application.dto.usuario.UsuarioUpdateDTO;
import distribuidora_api.application.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints de gerenciamento dos funcionários do sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Cadastrar um novo usuário no sistema", description = "Cria um novo cargo no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já existente no sistema.")
    })
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioCreateDTO dto) {
        UsuarioResponseDTO responseDTO = usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários no sistema", description = "Retorna uma lista com todos os usuários existentes no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuários retornados com sucesso."),
    })
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{email}")
    @Operation(summary = "Buscar um usuário pelo email", description = "Retorna um usuário específico com base no email fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados básicos de um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema de forma permanente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso (No Content)"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponseDTO> deletarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
