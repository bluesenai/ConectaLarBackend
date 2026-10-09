
package br.kaizen.ConectaLar.DTO;

import java.time.LocalDateTime;

public class PeticaoResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private Long autorId;
    private String nomeAutor;
    private long quantidadeAssinaturas;

    public PeticaoResponse() {
    }

    public PeticaoResponse(
            Long id,
            String titulo,
            String descricao,
            LocalDateTime dataCriacao,
            Long autorId,
            String nomeAutor,
            long quantidadeAssinaturas) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.autorId = autorId;
        this.nomeAutor = nomeAutor;
        this.quantidadeAssinaturas = quantidadeAssinaturas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public long getQuantidadeAssinaturas() {
        return quantidadeAssinaturas;
    }

    public void setQuantidadeAssinaturas(long quantidadeAssinaturas) {
        this.quantidadeAssinaturas = quantidadeAssinaturas;
    }
}