
package br.kaizen.ConectaLar.DTO;

import br.kaizen.ConectaLar.model.Notificacao;
import java.time.LocalDateTime;

public class NotificacaoResponse {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private boolean lida;

    public NotificacaoResponse(Notificacao notificacao) {
        this.id = notificacao.getId();
        this.titulo = notificacao.getTitulo();
        this.mensagem = notificacao.getMensagem();
        this.dataCriacao = notificacao.getDataCriacao();
        this.lida = notificacao.isLida();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public boolean isLida() {
        return lida;
    }
}
