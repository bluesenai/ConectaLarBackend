package br.kaizen.ConectaLar.DTO;

public class MetodoPagamentoResponse {

    private Long id;
    private String tipoChavePix;
    private String chavePix;

    public MetodoPagamentoResponse() {
    }

    public MetodoPagamentoResponse(
            Long id,
            String tipoChavePix,
            String chavePix) {
        this.id = id;
        this.tipoChavePix = tipoChavePix;
        this.chavePix = chavePix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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