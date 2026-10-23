
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.Notificacao;
import br.kaizen.ConectaLar.model.Reclamacao;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.repository.NotificacaoRepository;
import br.kaizen.ConectaLar.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificacaoService(
            NotificacaoRepository notificacaoRepository,
            UsuarioRepository usuarioRepository) {

        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void notificarComunidade(
            Reclamacao reclamacao) {

        Comunidade comunidade = reclamacao.getComunidade();

        List<Usuario> moradores =
        usuarioRepository.findByComunidadeId(
                comunidade.getId());

        for (Usuario morador : moradores) {

            Notificacao notificacao = new Notificacao();

            notificacao.setDestinatario(morador);
            notificacao.setReclamacao(reclamacao);
            notificacao.setDataCriacao(LocalDateTime.now());
            notificacao.setLida(false);

            notificacao.setTitulo(
                    "Alerta de " + reclamacao.getCategoria().name());

            notificacao.setMensagem(
                    "O limite de reclamações para a categoria "
                    + reclamacao.getCategoria().name()
                    + " foi atingido na sua comunidade.");

            notificacaoRepository.save(notificacao);
        }
    }

    public List<Notificacao> listarPorUsuario(
            Long usuarioId) {

        return notificacaoRepository
                .findByDestinatarioIdOrderByDataCriacaoDesc(usuarioId);
    }

    public long contarNaoLidas(Long usuarioId) {

        return notificacaoRepository
                .countByDestinatarioIdAndLidaFalse(usuarioId);
    }
    
@Transactional
public int marcarTodasComoLidas(Long usuarioId) {

    return notificacaoRepository
            .marcarTodasComoLidas(usuarioId);
}

}
