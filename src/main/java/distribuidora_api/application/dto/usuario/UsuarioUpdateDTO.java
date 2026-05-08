package distribuidora_api.application.dto.usuario;

import distribuidora_api.domain.enums.Cargo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioUpdateDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @Email(message = "Email inválido")
        @NotBlank(message = "O email é obrigatório")
        String email,

        @NotNull(message = "O cargo do usuário é obrigatório")
        Cargo cargo
) {
}
