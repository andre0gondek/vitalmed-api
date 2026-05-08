package distribuidora_api.infraestructure.config;

import distribuidora_api.domain.entity.Usuario;
import distribuidora_api.domain.enums.Cargo;
import distribuidora_api.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador Padrão");
            admin.setEmail("admin@vitalmed.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setCargo(Cargo.ADMINISTRADOR);

            usuarioRepository.save(admin);
            System.out.println("==================================================");
            System.out.println("Usuário Admin criado automaticamente.");
            System.out.println("Login: admin@vitalmed.com | Senha: admin123");
            System.out.println("==================================================");
        }
    }
}