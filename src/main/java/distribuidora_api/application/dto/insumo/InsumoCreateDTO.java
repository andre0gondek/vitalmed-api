package distribuidora_api.application.dto.insumo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InsumoCreateDTO(
        @NotBlank(message = "O nome do Insumo é obrigatório")
        String nomeInsumo,

        @NotNull(message = "A categoria é obrigatória")
        Long idCategoria,

        String capacidade,
        String tamanho,
        String material,

        @Min(value = 0, message = "O estoque mínimo não pode ser negativo")
        int estoqueMinimo,

        @Min(value = 0, message = "O estoque inicial não pode ser negativo")
        int estoqueAtual,

        LocalDate dataValidade
) {
}
