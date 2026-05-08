package distribuidora_api.ui_interface.exception;

import distribuidora_api.application.dto.ErroPadronizadoDTO;
import distribuidora_api.domain.exception.ConflitoDeDadosException;
import distribuidora_api.domain.exception.RecursoNaoEncontradoException;
import distribuidora_api.domain.exception.RegraDeNegocioException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Tratamento para Entidades não encontradas (Retorna 404)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex){
        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso Não Encontrado",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 2. Tratamento para Regras de Negócio, ex: Estoque Insuficiente (Retorna 400)
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarRegraDeNegocio(RegraDeNegocioException ex){
        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Violação de Regra de Negocio",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 3. Tratamento para Conflito de Dados, ex: Email já existe (Retorna 409)
    @ExceptionHandler(ConflitoDeDadosException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarConflitoDeDados(ConflitoDeDadosException ex){
        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de Dados",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    // 4. Tratamento padrão do @Valid, ex: Campos vazios no JSON (Retorna 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarErroDeFormulario(MethodArgumentNotValidException ex) {
        String camposComErro = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação dos Campos",
                camposComErro
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

}
