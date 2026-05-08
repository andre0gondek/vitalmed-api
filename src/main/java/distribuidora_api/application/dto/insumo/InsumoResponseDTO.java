package distribuidora_api.application.dto.insumo;

import java.time.LocalDate;

public record InsumoResponseDTO(
        Long id,
        String nomeInsumo,
        Long idCategoria,
        String nomeCategoria,
        String capacidade,
        String tamanho,
        String material,
        int estoqueMinimo,
        int estoqueAtual,
        LocalDate dataValidade,
        boolean alertaEstoque
) {
}
