
package br.kaizen.ConectaLar.DTO;

import br.kaizen.ConectaLar.model.CategoriaReclamacao;

public class RegistrarReclamacaoRequest {

    private Long usuarioId;
    private String senha;
    private CategoriaReclamacao categoria;

    public RegistrarReclamacaoRequest() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public CategoriaReclamacao getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaReclamacao categoria) {
        this.categoria = categoria;
    }
}
