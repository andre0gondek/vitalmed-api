package distribuidora_api.domain.entity;

import distribuidora_api.domain.enums.Cargo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank(message = "O nome do usuário é obrigatório")
    @Column(name = "nome_usuario", nullable = false, length = 40)
    private String nome;

    @Email(message = "O email deve ser válido")
    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Cargo cargo;

    // --- MÉTODOS OBRIGATÓRIOS DO USERDETAILS ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mapeia o Enum Cargo para o padrão de roles do Spring
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.cargo.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email; // O nosso login é feito pelo email!
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
