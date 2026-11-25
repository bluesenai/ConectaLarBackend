package br.kaizen.ConectaLar.DTO;

import java.util.List;

public class SegurancaResponse {

    private String nomePrestador;
    private List<DisponibilidadeRondaResponse> horarios;

    public SegurancaResponse() {
    }

    public SegurancaResponse(
            String nomePrestador,
            List<DisponibilidadeRondaResponse> horarios) {
        this.nomePrestador = nomePrestador;
        this.horarios = horarios;
    }

    public String getNomePrestador() {
        return nomePrestador;
    }

    public void setNomePrestador(String nomePrestador) {
        this.nomePrestador = nomePrestador;
    }

    public List<DisponibilidadeRondaResponse> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<DisponibilidadeRondaResponse> horarios) {
        this.horarios = horarios;
    }
}