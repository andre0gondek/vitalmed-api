package distribuidora_api.application.dto.movimentacao;

import distribuidora_api.domain.enums.TipoMovimentacao;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovimentacaoCreateDTO(
        @NotNull(message = "O ID do insumo é obrigatório")
        Long idInsumo,

        @NotNull(message = "O ID do usuário responsável é obrigatório")
        Long idUsuario,

        @NotNull(message = "O tipo de movimentação é obrigatório")
        TipoMovimentacao tipoMovimentacao,

        @Min(value = 1, message = "A quantidade deve ser maior que zero")
        int quantidade,

        @NotBlank(message = "A finalidade/observação é obrigatória")
        String finalidade
) {
}
