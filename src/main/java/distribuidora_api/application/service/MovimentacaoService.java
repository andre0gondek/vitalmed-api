package distribuidora_api.application.service;

import distribuidora_api.application.dto.movimentacao.MovimentacaoCreateDTO;
import distribuidora_api.application.dto.movimentacao.MovimentacaoResponseDTO;
import distribuidora_api.domain.entity.Insumo;
import distribuidora_api.domain.entity.Movimentacao;
import distribuidora_api.domain.entity.Usuario;
import distribuidora_api.domain.enums.TipoMovimentacao;
import distribuidora_api.domain.exception.RecursoNaoEncontradoException;
import distribuidora_api.domain.exception.RegraDeNegocioException;
import distribuidora_api.domain.repository.InsumoRepository;
import distribuidora_api.domain.repository.MovimentacaoRepository;
import distribuidora_api.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final InsumoRepository insumoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public MovimentacaoResponseDTO registrarMovimentacao(MovimentacaoCreateDTO dto) {
        // 1. Busca os dados no banco
        Insumo insumo = insumoRepository.findById(dto.idInsumo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Insumo não encontrado."));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        // 2. Regra de Negócio: Calcula o novo estoque
        if (dto.tipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            insumo.setEstoqueAtual(insumo.getEstoqueAtual() + dto.quantidade());
        } else if (dto.tipoMovimentacao() == TipoMovimentacao.SAIDA) {
            if (dto.quantidade() > insumo.getEstoqueAtual()) {
                throw new RegraDeNegocioException(
                        "Estoque insuficiente! Estoque atual: " + insumo.getEstoqueAtual()
                );
            }
            insumo.setEstoqueAtual(insumo.getEstoqueAtual() - dto.quantidade());
        }

        // 3. Monta o registro histórico
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setInsumo(insumo);
        movimentacao.setUsuario(usuario);
        movimentacao.setTipoMovimentacao(dto.tipoMovimentacao());
        movimentacao.setQuantidade(dto.quantidade());
        movimentacao.setFinalidade(dto.finalidade());

        // 4. Salva a movimentação
        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);
        insumoRepository.save(insumo);

        return converterParaResponse(movimentacaoSalva);
    }

    public List<MovimentacaoResponseDTO> listarTodas() {
        return movimentacaoRepository.findAll().stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }


    private MovimentacaoResponseDTO converterParaResponse(Movimentacao m) {
        return new MovimentacaoResponseDTO(
                m.getId(),
                m.getInsumo().getId(),
                m.getInsumo().getNomeInsumo(),
                m.getUsuario().getId(),
                m.getUsuario().getNome(),
                m.getTipoMovimentacao(),
                m.getQuantidade(),
                m.getDataHora(),
                m.getFinalidade()
        );
    }
}
