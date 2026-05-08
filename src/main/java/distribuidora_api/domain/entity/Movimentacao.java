package distribuidora_api.domain.entity;

import distribuidora_api.domain.enums.TipoMovimentacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Movimentacao")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Long id;

    @NotNull(message = "O insumo é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_insumo", nullable = false)
    private Insumo insumo;

    @NotNull(message = "O usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false)
    private TipoMovimentacao tipoMovimentacao;

    @Min(value = 1, message = "O quantidade movimentada deve ser maior ou igual a 1")
    @Column(name = "qtd", nullable = false)
    private int quantidade;

    @CreationTimestamp
    @Column(name = "data_hora", nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @Column(length = 255)
    private String finalidade;
}
