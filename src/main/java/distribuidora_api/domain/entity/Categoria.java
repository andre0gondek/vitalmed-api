package distribuidora_api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "categoria")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório!")
    @Column(name = "nome_categoria",length = 100, nullable = false)
    private String nomeCategoria;
}
