package distribuidora_api.application.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaCreateDTO(
        @NotBlank(message = "O nome da categoria é obrigatório")
        String nomeCategoria
) {
}
