
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.DTO.PeticaoResponse;
import br.kaizen.ConectaLar.model.AssinaturaPeticao;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.Peticao;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.repository.AssinaturaPeticaoRepository;
import br.kaizen.ConectaLar.repository.PeticaoRepository;
import br.kaizen.ConectaLar.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PeticaoService {

    private final PeticaoRepository peticaoRepository;
    private final AssinaturaPeticaoRepository assinaturaRepository;
    private final UsuarioRepository usuarioRepository;

    public PeticaoService(
            PeticaoRepository peticaoRepository,
            AssinaturaPeticaoRepository assinaturaRepository,
            UsuarioRepository usuarioRepository) {

        this.peticaoRepository = peticaoRepository;
        this.assinaturaRepository = assinaturaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public PeticaoResponse criarPeticao(
            Long usuarioId,
            String titulo,
            String descricao) {

        Usuario autor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Comunidade comunidade = autor.getComunidade();

        if (comunidade == null) {
            throw new RuntimeException(
                    "O usuário não pertence a nenhuma comunidade");
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RuntimeException(
                    "O título da petição é obrigatório");
        }

        if (descricao == null || descricao.trim().isEmpty()) {
            throw new RuntimeException(
                    "O motivo da petição é obrigatório");
        }

        Peticao peticao = new Peticao();
        peticao.setTitulo(titulo.trim());
        peticao.setDescricao(descricao.trim());
        peticao.setDataCriacao(LocalDateTime.now());
        peticao.setAutor(autor);
        peticao.setComunidade(comunidade);

        Peticao peticaoSalva = peticaoRepository.save(peticao);

        return converterParaResponse(peticaoSalva);
    }

    public List<PeticaoResponse> listarPeticoes(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        if (usuario.getComunidade() == null) {
            throw new RuntimeException(
                    "O usuário não pertence a nenhuma comunidade");
        }

        Long comunidadeId = usuario.getComunidade().getId();

        List<Peticao> peticoes = peticaoRepository
                .findByComunidadeIdOrderByDataCriacaoDesc(comunidadeId);

        return peticoes.stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional
    public void assinarPeticao(Long usuarioId, Long peticaoId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Peticao peticao = peticaoRepository.findById(peticaoId)
                .orElseThrow(() ->
                        new RuntimeException("Petição não encontrada"));

        if (usuario.getComunidade() == null
                || !usuario.getComunidade().getId()
                        .equals(peticao.getComunidade().getId())) {

            throw new RuntimeException(
                    "Você só pode assinar petições da sua comunidade");
        }

        if (peticao.getAutor().getId().equals(usuarioId)) {
            throw new RuntimeException(
                    "O autor não pode assinar a própria petição");
        }

        if (assinaturaRepository
                .existsByPeticaoIdAndUsuarioId(peticaoId, usuarioId)) {

            throw new RuntimeException(
                    "Você já assinou esta petição");
        }

        AssinaturaPeticao assinatura = new AssinaturaPeticao();
        assinatura.setPeticao(peticao);
        assinatura.setUsuario(usuario);
        assinatura.setDataAssinatura(LocalDateTime.now());

        assinaturaRepository.save(assinatura);
    }

    public long contarAssinaturas(Long peticaoId) {

        if (!peticaoRepository.existsById(peticaoId)) {
            throw new RuntimeException("Petição não encontrada");
        }

        return assinaturaRepository.countByPeticaoId(peticaoId);
    }

    private PeticaoResponse converterParaResponse(Peticao peticao) {

        return new PeticaoResponse(
                peticao.getId(),
                peticao.getTitulo(),
                peticao.getDescricao(),
                peticao.getDataCriacao(),
                peticao.getAutor().getId(),
                peticao.getAutor().getNome(),
                assinaturaRepository.countByPeticaoId(peticao.getId())
        );
    }
}