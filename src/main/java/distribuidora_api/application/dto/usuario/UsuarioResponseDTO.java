package distribuidora_api.application.dto.usuario;

import distribuidora_api.domain.enums.Cargo;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Cargo cargo
) {
}
