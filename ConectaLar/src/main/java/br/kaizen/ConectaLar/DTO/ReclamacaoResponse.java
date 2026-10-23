
package br.kaizen.ConectaLar.DTO;

import br.kaizen.ConectaLar.model.CategoriaReclamacao;
import br.kaizen.ConectaLar.model.NivelRelevancia;
import br.kaizen.ConectaLar.model.Reclamacao;

import java.time.LocalDateTime;

public class ReclamacaoResponse {

    private Long id;
    private String nomeAutor;
    private CategoriaReclamacao categoria;
    private NivelRelevancia nivelRelevancia;
    private LocalDateTime dataCriacao;
    private boolean alertaEmitido;

    public ReclamacaoResponse() {
    }

    public ReclamacaoResponse(Reclamacao reclamacao) {
        this.id = reclamacao.getId();
        this.nomeAutor = reclamacao.getAutor().getNome();
        this.categoria = reclamacao.getCategoria();
        this.nivelRelevancia = reclamacao.getNivelRelevancia();
        this.dataCriacao = reclamacao.getDataCriacao();
        this.alertaEmitido = reclamacao.isAlertaEmitido();
    }

    public Long getId() {
        return id;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public CategoriaReclamacao getCategoria() {
        return categoria;
    }

    public NivelRelevancia getNivelRelevancia() {
        return nivelRelevancia;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public boolean isAlertaEmitido() {
        return alertaEmitido;
    }
}
