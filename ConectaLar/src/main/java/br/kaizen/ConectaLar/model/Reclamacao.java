
package br.kaizen.ConectaLar.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reclamacao")
public class Reclamacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private CategoriaReclamacao categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelRelevancia nivelRelevancia;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private boolean alertaEmitido = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comunidade_id", nullable = false)
    private Comunidade comunidade;

    public Reclamacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaReclamacao getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaReclamacao categoria) {
        this.categoria = categoria;
    }

    public NivelRelevancia getNivelRelevancia() {
        return nivelRelevancia;
    }

    public void setNivelRelevancia(NivelRelevancia nivelRelevancia) {
        this.nivelRelevancia = nivelRelevancia;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isAlertaEmitido() {
        return alertaEmitido;
    }

    public void setAlertaEmitido(boolean alertaEmitido) {
        this.alertaEmitido = alertaEmitido;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Comunidade getComunidade() {
        return comunidade;
    }

    public void setComunidade(Comunidade comunidade) {
        this.comunidade = comunidade;
    }
}