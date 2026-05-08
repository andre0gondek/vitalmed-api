package distribuidora_api.application.dto;

import java.time.LocalDateTime;

public record ErroPadronizadoDTO(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        String mensagem
) {
}
