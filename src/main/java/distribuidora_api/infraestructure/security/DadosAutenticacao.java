package distribuidora_api.infraestructure.security;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank String email,
        @NotBlank String senha
) {
}
