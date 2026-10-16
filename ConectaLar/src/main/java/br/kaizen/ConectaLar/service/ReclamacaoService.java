
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.DTO.ReclamacaoResponse;
import br.kaizen.ConectaLar.DTO.RegistrarReclamacaoRequest;
import br.kaizen.ConectaLar.model.CategoriaReclamacao;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.NivelRelevancia;
import br.kaizen.ConectaLar.model.Reclamacao;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.repository.ReclamacaoRepository;
import br.kaizen.ConectaLar.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReclamacaoService {

    private final ReclamacaoRepository reclamacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public ReclamacaoService(
            ReclamacaoRepository reclamacaoRepository,
            UsuarioRepository usuarioRepository) {
        this.reclamacaoRepository = reclamacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public NivelRelevancia definirNivel(
            CategoriaReclamacao categoria) {

        return switch (categoria) {
            case QUEDA_ENERGIA, FALTA_AGUA ->
                    NivelRelevancia.NORMAL;
            case ACIDENTE, FIO_SOLTO, ASSALTO ->
                    NivelRelevancia.IMPORTANTE;
            case ANIMAL_PERDIDO ->
                    NivelRelevancia.BAIXA;
        };
    }

    public int definirLimite(NivelRelevancia nivel) {
        return switch (nivel) {
            case IMPORTANTE -> 3;
            case NORMAL -> 2;
            case BAIXA -> 1;
        };
    }

    public long contarReclamacoesUltimas24Horas(
            Long comunidadeId,
            CategoriaReclamacao categoria) {

        LocalDateTime inicio = LocalDateTime.now().minusHours(24);

        return reclamacaoRepository.contarReclamacoesRecentes(
                comunidadeId,
                categoria,
                definirNivel(categoria),
                inicio
        );
    }

    @Transactional
    public boolean registrarReclamacao(
            RegistrarReclamacaoRequest request) {

        if (request.getUsuarioId() == null
                || request.getSenha() == null
                || request.getSenha().isBlank()
                || request.getCategoria() == null) {
            throw new RuntimeException(
                    "Informe o usuário, a senha e a categoria");
        }

        Usuario usuario = usuarioRepository
                .findById(request.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        if (!request.getSenha().equals(usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        Comunidade comunidade = usuario.getComunidade();

        if (comunidade == null) {
            throw new RuntimeException(
                    "O usuário não pertence a uma comunidade");
        }

        CategoriaReclamacao categoria = request.getCategoria();
        NivelRelevancia nivel = definirNivel(categoria);
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicio = agora.minusHours(24);

        Reclamacao reclamacao = new Reclamacao();
        reclamacao.setAutor(usuario);
        reclamacao.setComunidade(comunidade);
        reclamacao.setCategoria(categoria);
        reclamacao.setNivelRelevancia(nivel);
        reclamacao.setDataCriacao(agora);
        reclamacao.setAlertaEmitido(false);

        reclamacaoRepository.save(reclamacao);

        long quantidade = reclamacaoRepository
                .contarReclamacoesRecentes(
                        comunidade.getId(),
                        categoria,
                        nivel,
                        inicio
                );

        int limite = definirLimite(nivel);

        if (quantidade < limite) {
            return false;
        }

        boolean alertaJaEmitido = reclamacaoRepository
                .existsByComunidadeIdAndCategoriaAndDataCriacaoGreaterThanEqualAndAlertaEmitidoTrue(
                        comunidade.getId(),
                        categoria,
                        inicio
                );

        if (alertaJaEmitido) {
            return false;
        }

        reclamacao.setAlertaEmitido(true);
        reclamacaoRepository.save(reclamacao);

        return true;
    }

   
public List<ReclamacaoResponse> listarPorComunidade(
        Long comunidadeId) {

    return reclamacaoRepository
            .findByComunidadeIdOrderByDataCriacaoDesc(comunidadeId)
            .stream()
            .map(ReclamacaoResponse::new)
            .toList();
}
}
