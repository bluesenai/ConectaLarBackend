package br.kaizen.ConectaLar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "metodo_pagamento")
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prestador_id", nullable = false)
    private PrestadorServico prestador;

    @Column(nullable = false)
    private String tipoChavePix;

    @Column(nullable = false, unique = true)
    private String chavePix;

    public MetodoPagamento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrestadorServico getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorServico prestador) {
        this.prestador = prestador;
    }

    public String getTipoChavePix() {
        return tipoChavePix;
    }

    public void setTipoChavePix(String tipoChavePix) {
        this.tipoChavePix = tipoChavePix;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }
}