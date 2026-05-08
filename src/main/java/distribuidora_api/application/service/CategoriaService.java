package distribuidora_api.application.service;

import distribuidora_api.application.dto.categoria.CategoriaCreateDTO;
import distribuidora_api.application.dto.categoria.CategoriaResponseDTO;
import distribuidora_api.domain.entity.Categoria;
import distribuidora_api.domain.exception.RecursoNaoEncontradoException;
import distribuidora_api.domain.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO criarCategoria(CategoriaCreateDTO dto){
        Categoria categoria =  new Categoria();
        categoria.setNomeCategoria(dto.nomeCategoria());

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return converterParaResponse(categoriaSalva);
    }

    public List<CategoriaResponseDTO> listarTodas(){
        return categoriaRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
        return converterParaResponse(categoria);
    }

    private CategoriaResponseDTO converterParaResponse(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNomeCategoria()
        );
    }
}
