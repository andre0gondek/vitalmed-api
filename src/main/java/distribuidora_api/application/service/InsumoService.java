package distribuidora_api.application.service;

import distribuidora_api.application.dto.insumo.InsumoCreateDTO;
import distribuidora_api.application.dto.insumo.InsumoResponseDTO;
import distribuidora_api.domain.entity.Categoria;
import distribuidora_api.domain.entity.Insumo;
import distribuidora_api.domain.exception.RecursoNaoEncontradoException;
import distribuidora_api.domain.repository.CategoriaRepository;
import distribuidora_api.domain.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;
    private final CategoriaRepository categoriaRepository;

    public InsumoResponseDTO cadastrarinsumo(InsumoCreateDTO dto){
        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada."));

        Insumo insumo = new Insumo();
        insumo.setNomeInsumo(dto.nomeInsumo());
        insumo.setCategoria(categoria);
        insumo.setCapacidade(dto.capacidade());
        insumo.setTamanho(dto.tamanho());
        insumo.setMaterial(dto.material());
        insumo.setEstoqueMinimo(dto.estoqueMinimo());
        insumo.setEstoqueAtual(dto.estoqueAtual());
        insumo.setValidade(dto.dataValidade());

        Insumo insumoSalvo = insumoRepository.save(insumo);
        return converterParaResponse(insumoSalvo);
    }

    public List<InsumoResponseDTO> listarTodos(){
           return insumoRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public List<InsumoResponseDTO> listarItensEmAlerta(){
        return insumoRepository.findInsumosEmAlerta().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    private InsumoResponseDTO converterParaResponse(Insumo insumo) {
        // A mágica da nossa regra de negócio do alerta acontece aqui
        boolean isAlerta = insumo.getEstoqueAtual() <= insumo.getEstoqueMinimo();

        return new InsumoResponseDTO(
                insumo.getId(),
                insumo.getNomeInsumo(),
                insumo.getCategoria().getId(),
                insumo.getCategoria().getNomeCategoria(),
                insumo.getCapacidade(),
                insumo.getTamanho(),
                insumo.getMaterial(),
                insumo.getEstoqueMinimo(),
                insumo.getEstoqueAtual(),
                insumo.getValidade(),
                isAlerta
        );
    }
}
