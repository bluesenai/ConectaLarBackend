
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.DTO.MensagemResponse;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.Mensagem;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.repository.MensagemRepository;
import br.kaizen.ConectaLar.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final UsuarioRepository usuarioRepository;

    public MensagemService(
            MensagemRepository mensagemRepository,
            UsuarioRepository usuarioRepository) {

        this.mensagemRepository = mensagemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public MensagemResponse enviarMensagem(Long usuarioId, String texto) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        if (usuario.getComunidade() == null) {
            throw new RuntimeException(
                    "Usuário não pertence a nenhuma comunidade");
        }

        if (texto == null || texto.trim().isEmpty()) {
            throw new RuntimeException(
                    "A mensagem não pode estar vazia");
        }

        Comunidade comunidade = usuario.getComunidade();

        Mensagem mensagem = new Mensagem();
        mensagem.setTexto(texto);
        mensagem.setDataHora(LocalDateTime.now());
        mensagem.setUsuario(usuario);
        mensagem.setComunidade(comunidade);

        Mensagem mensagemSalva = mensagemRepository.save(mensagem);

        return converterParaResponse(mensagemSalva);
    }

    public List<MensagemResponse> buscarMensagens(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        if (usuario.getComunidade() == null) {
            throw new RuntimeException(
                    "Usuário não pertence a nenhuma comunidade");
        }

        Long comunidadeId = usuario.getComunidade().getId();

        List<Mensagem> mensagens =
                mensagemRepository
                        .findByComunidadeIdOrderByDataHoraAsc(comunidadeId);

        return mensagens.stream()
                .map(this::converterParaResponse)
                .toList();
    }

    private MensagemResponse converterParaResponse(Mensagem mensagem) {

        return new MensagemResponse(
                mensagem.getId(),
                mensagem.getTexto(),
                mensagem.getDataHora(),
                mensagem.getUsuario().getId(),
                mensagem.getUsuario().getNome()
        );
    }
}