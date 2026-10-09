
package br.kaizen.ConectaLar.DTO;

import java.time.LocalDateTime;

public class MensagemResponse {

    private Long id;
    private String texto;
    private LocalDateTime dataHora;
    private Long usuarioId;
    private String nomeUsuario;

    public MensagemResponse() {
    }

    public MensagemResponse(
            Long id,
            String texto,
            LocalDateTime dataHora,
            Long usuarioId,
            String nomeUsuario) {

        this.id = id;
        this.texto = texto;
        this.dataHora = dataHora;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}