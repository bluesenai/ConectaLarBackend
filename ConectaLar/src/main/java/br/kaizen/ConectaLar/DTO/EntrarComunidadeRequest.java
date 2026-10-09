package br.kaizen.ConectaLar.DTO;

public class EntrarComunidadeRequest {

    private Long usuarioId;
    private String cep;

    public EntrarComunidadeRequest() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}