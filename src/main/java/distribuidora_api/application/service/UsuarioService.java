package distribuidora_api.application.service;

import distribuidora_api.application.dto.usuario.UsuarioCreateDTO;
import distribuidora_api.application.dto.usuario.UsuarioResponseDTO;
import distribuidora_api.application.dto.usuario.UsuarioUpdateDTO;
import distribuidora_api.domain.entity.Usuario;
import distribuidora_api.domain.exception.ConflitoDeDadosException;
import distribuidora_api.domain.exception.RecursoNaoEncontradoException;
import distribuidora_api.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new ConflitoDeDadosException("Email já cadastrado no sistema.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setSenha(new BCryptPasswordEncoder().encode(dto.senha()));
        novoUsuario.setCargo(dto.cargo());

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return converterParaResponse(usuarioSalvo);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado."));

        return converterParaResponse(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        if (!usuarioExistente.getEmail().equals(dto.email()) &&
                usuarioRepository.existsByEmail(dto.email())) {
            throw new ConflitoDeDadosException("Este email já está associado a outro usuário no sistema.");
        }

        usuarioExistente.setNome(dto.nome());
        usuarioExistente.setEmail(dto.email());
        usuarioExistente.setCargo(dto.cargo());

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return converterParaResponse(usuarioAtualizado);
    }

    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);

    }

    private UsuarioResponseDTO converterParaResponse(Usuario usuario) {
        // Usando o construtor gerado automaticamente pelo Record
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCargo()
        );
    }
}
