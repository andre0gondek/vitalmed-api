package distribuidora_api.application.dto.movimentacao;

import distribuidora_api.domain.enums.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponseDTO(
        Long id,
        Long idInsumo,
        String nomeInsumo,
        Long idUsuario,
        String nomeUsuarioResponsavel,
        TipoMovimentacao tipoMovimentacao,
        int quantidade,
        LocalDateTime dataHora,
        String finalidade
) {
}
