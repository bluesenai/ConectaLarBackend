package br.kaizen.ConectaLar.DTO;

public class MetodoPagamentoRequest {

    private String tipoChavePix;
    private String chavePix;

    public MetodoPagamentoRequest() {
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