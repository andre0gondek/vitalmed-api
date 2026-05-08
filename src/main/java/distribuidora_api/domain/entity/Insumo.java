package distribuidora_api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Insumo")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do insumo é obrigatório!")
    @Column(name = "nome_insumo", nullable = false, length = 150)
    private String nomeInsumo;

    // --- CHAVE ESTRANGEIRA ---
    @NotNull(message = "A categoria é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(length = 20)
    private String capacidade;

    @Column(length = 10)
    private String tamanho;

    @Column(length = 50)
    private String material;

    @Min(value = 0, message = "O estoque mínimo não pode ser negativo!")
    @Column(name = "estoque_minimo", nullable = false)
    private int estoqueMinimo;

    @Min(value = 0, message = "O estoque atual não pode ser negativo!")
    @Column(name = "estoque_atual",  nullable = false)
    private int estoqueAtual;

    @Column(name = "validade")
    private LocalDate validade;
}
